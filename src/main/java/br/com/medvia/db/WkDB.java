package br.com.medvia.db;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Willian Kirschner
 * @version 2015 Ago, 01
 * @param <T> Class that represent a Table in DB, need to extends WkTable
 */
public class WkDB<T extends WkTable> {

    private static final String ID = "id";

    private static final String jdbc_driver = "org.sqlite.JDBC";
    private static final String url_in_file = "jdbc:sqlite:";
    private static File dirDB = null;
    private static File fileDB = null;
    // Key = 'Table Name' Value = 'Connection/Session'
    private static final HashMap<String, Connection> connections = new HashMap<>(1);
    private final Connection conn;
    private PreparedStatement ps;
    private boolean showSQL = false;
    private final String tableName;
    private final Class classDB;
    // Key: nome do campo; Value: Campo
    private final HashMap<String, Field> fields = new HashMap<>();
    private final String sqlInsert;

    public static class Update {

        public static UpdateField fields(String... fields) {
            return new UpdateField(fields);
        }

        public static UpdateValue values(Object... values) {
            return new UpdateValue(values);
        }
    }

    public static class Where {

        public static WhereField fields(String... fields) {
            return new WhereField(fields);
        }

        /**
         * @param conditions Normal comparisons and more: 'like', 'between', ...
         * @return Where condictions to be used by WkDB
         */
        public static WhereCondition conditions(String... conditions) {
            return new WhereCondition(conditions);
        }

        public static WhereValue values(Object... values) {
            return new WhereValue(values);
        }
    }

    private static class UpdateField {

        private final String[] fields;

        public UpdateField(String... fields) {
            this.fields = fields;
        }

        public String[] getFields() {
            return fields;
        }

    }

    private static class UpdateValue {

        private final Object[] values;

        public UpdateValue(Object... values) {
            this.values = values;
        }

        public Object[] getValues() {
            return values;
        }
    }

    private static class WhereField {

        private final String[] fields;

        public WhereField(String... fields) {
            this.fields = fields;
        }

        public String[] getFields() {
            return fields;
        }
    }

    private static class WhereCondition {

        private final String[] conditions;

        public WhereCondition(String... conditions) {
            this.conditions = conditions;
        }

        public String[] getConditions() {
            return conditions;
        }

    }

    private static class WhereValue {

        private final Object[] values;

        public WhereValue(Object... values) {
            // Se recebeu NULL, significa que inseriu apenas um parâmetro e o valor deste é NULL
            if (values == null) {
                this.values = new Object[]{null};
            } else {
                this.values = values;
            }
        }

        public Object[] getValues() {
            return values;
        }

    }

    /**
     * Specifies an directory to save the Data Base
     *
     * @param directoryDB A direwctory when the tables will be save on hard disk.
     */
    public static void setDirDB(File directoryDB) {
        if (directoryDB != null && directoryDB.isDirectory()) {
            dirDB = directoryDB;
        }
    }

    public static File getDirDB() {
        return dirDB;
    }

    public static void setFileDB(File file) {
        if (file != null) {
            fileDB = file;
        }
    }

    /**
     * Create a connection for this Table. It will be performed just in memory.
     *
     * @param clazz Class that represent a Table. This class need extends WkTable
     */
    public WkDB(Class<T> clazz) {
        this(clazz, false);
    }

    /**
     * Create a connection for this Table. If a directory was not especified to save the Data Base, it will be performed
     * just in memory. If a directory was especified, it will be allowed to save in a hard disk.
     *
     * @param clazz Class that represent a Table. This class need extends WkTable
     * @param saveInDisk Specifies whether must save the table in hard disk
     */
    public WkDB(Class<T> clazz, boolean saveInDisk) {
        Class superclass = clazz.getSuperclass();
        if (superclass == null || superclass != WkTable.class) {
            throw new ExceptionInInitializerError(clazz.getName() + " is not WkTable type!");
        }
        tableName = clazz.getSimpleName();
        classDB = clazz;
        // Cria uma connection ou pega uma que já existe para a tabela
        Connection c = connections.get(tableName);
        try {
            if (c.isClosed()) {
                c = null;
            }
        } catch (Exception ex) {
            c = null;
        }
        // Se não encontrou uma conexão válida
        if (c == null) {
            try {
                conn = createConnection(saveInDisk);
                connections.put(tableName, conn);
            } catch (Exception ex) {
                println("Unable to create a connection for table!");
                throw new ExceptionInInitializerError(ex);
            }
        } else {
            conn = c;
        }
        // Armazena os campos válidos
        for (Field field : classDB.getDeclaredFields()) {
            if (!Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                fields.put(field.getName(), field);
            }
        }
        int countF = fields.size();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        StringBuilder sqlValues = new StringBuilder(countF * 2);
        sql.append(tableName);
        sql.append(" (");
        try {
            for (Field field : fields.values()) {
                sql.append(field.getName());
                if ((--countF) > 0) {
                    sql.append(",");
                    sqlValues.append("?,");
                } else {
                    sqlValues.append("?");
                }
            }
            sql.append(") VALUES (");
            sql.append(sqlValues.toString());
            sql.append(")");
        } catch (Exception ex) {
            println("Insert-Init", ex.toString());
        }
        sqlInsert = sql.toString();
    }

    private Connection createConnection(boolean saveInDisk) throws Exception {
        Class.forName(jdbc_driver);
        File dbFile;
        // Se definiu o arquivo de banco de dados, DEVE salvar em um único arquivo
        if (fileDB != null) {
            dbFile = fileDB;
        } else {
            // Se não especificou um diretório para salvar o DB ou não deve gravar em disco
            if (!saveInDisk) {
                println("Creating new connection JDBC = " + url_in_file + ":memory:");
                return DriverManager.getConnection(url_in_file + ":memory:");
            }
            // Se ainda não definiu pasta paa salvar o banco em disco, utiliza home como padrão
            if (dirDB == null) {
                dirDB = new File(System.getProperty("user.home", ""));
            }
            dbFile = new File(dirDB.getAbsolutePath(), tableName + "_" + System.currentTimeMillis() + ".db");
        }
        println("Creating new connection JDBC = " + url_in_file + dbFile);
        return DriverManager.getConnection(url_in_file + dbFile);
    }

    public static void closeAll() {
        // Finaliza todas as conexões
        for (Connection c : connections.values()) {
            try {
                c.close();
            } catch (Exception e) {
            }
        }
    }

    public void close() {
        try {
            conn.close();
            println("Closed!");
        } catch (SQLException ex) {
            println("Close", ex.toString());
        }
    }

    public boolean dropAndCreateTable() {
        statementExecute("DROP TABLE " + tableName, false);
        return createTable();
    }

    public boolean createTable() {
        if (count() != null) {
            println("CreateTable", "Table already exist!");
            return true;
        }
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(tableName);
        sql.append(" (id INTEGER PRIMARY KEY NOT NULL, ");
        int index = fields.size();
        for (Field field : fields.values()) {
            sql.append(field.getName());
            if (field.getType() == String.class) {
                sql.append(" TEXT");
            } else if (isSameType(field.getType(), Boolean.class)) {
                sql.append(" BOOLEAN NOT NULL DEFAULT 0");
            } else if (isSameType(field.getType(), Integer.class)) {
                sql.append(" INTEGER DEFAULT 0");
            } else if (isSameType(field.getType(), Long.class)) {
                sql.append(" BIGINT DEFAULT 0");
            } else {
                sql.append(" VARCHAR(10)");
            }
            if ((--index) > 0) {
                sql.append(", ");
            }
        }
        sql.append(")");
        return statementExecute(sql.toString(), true);
    }

    private boolean isSameType(Class<?> type, Class<?> typeToCompare) {
        if (type != null && typeToCompare != null) {
            if (type == typeToCompare
                    || (type.isPrimitive() && typeToCompare.getSimpleName().toLowerCase().startsWith(type.getName()))) {
                return true;
            }
        }
        return false;
    }

    private boolean statementExecute(String sql, boolean showError) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception ex) {
            if (showError) {
                ex.printStackTrace();
            }
        } finally {
            closeSafe(stmt);
        }
        return false;
    }

    public boolean startTransaction() {
        try {
            conn.setAutoCommit(false);
            return true;
        } catch (Exception ex) {
            println("StartTransaction", ex.toString());
        }
        return false;
    }

    public boolean flushTransaction() {
        try {
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (Exception ex) {
            println("FlushTransaction", ex.toString());
        }
        return false;
    }

    public boolean insert(WkTable objDBElement) {
        if (objDBElement == null) {
            return false;
        }
        ArrayList<Object> values = new ArrayList<>(fields.size());
        ResultSet rs = null;
        try {
            for (Field field : fields.values()) {
                values.add(field.get(objDBElement));
            }
            ps = conn.prepareStatement(sqlInsert);
            int index = 0;
            for (Object value : values) {
                ps.setObject((++index), value);
            }
            ps.execute();
            rs = ps.getGeneratedKeys();
            // Insere a PK que foi gerada
            if (rs.next()) {
                objDBElement.setId(rs.getInt(1));
            } else {
                objDBElement.setId(-1);
                println("Insert", "PRIMARY KEY NOT GENERATED! " + objDBElement);
            }
            return true;
        } catch (Exception ex) {
            println("Insert", ex.toString());
            ex.printStackTrace();
        } finally {
            closeSafe(rs);
            closeSafe(ps);
        }
        return false;
    }

    /**
     * A builder used to provide to show the SQL which is execute.
     *
     * @return The object itself to continue using normally.
     */
    public WkDB<T> showSQL() {
        showSQL = true;
        return this;
    }

    /**
     * SELECT COUNT(ID) FROM TABLE
     *
     * @return Number of records found
     */
    public Integer count() {
        return count(null, null);
    }

    /**
     * SELECT COUNT(field) FROM TABLE [extraCondition]
     *
     * @param field Field to be counted
     * @param extraCondition Optional!
     * @return Number of records found
     */
    public Integer count(String field, String extraCondition) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            StringBuilder sql = new StringBuilder("SELECT COUNT(");
            sql.append(field == null ? ID : field).append(") AS C FROM ").append(tableName);
            if (extraCondition != null) {
                sql.append(" ").append(extraCondition);
            }
            showSQL(sql);
            rs = stmt.executeQuery(sql.toString());
            return rs.getInt("C");
        } catch (Exception ex) {
        } finally {
            closeSafe(rs);
            closeSafe(stmt);
            showSQL = false;
        }
        return null;
    }

    public T selectByID(int id) {
        List<T> select = select(null, Where.fields(ID), Where.conditions("="), Where.values(id));
        if (select.isEmpty()) {
            return null;
        }
        return select.get(0);
    }

    /**
     * SELECT * FROM TABLE
     *
     * @return
     */
    public List<T> selectAll() {
        return select(null, null);
    }

    /**
     * SELECT * FROM TABLE [extraCondition]
     *
     * @param extraCondition Optional!
     * @return
     */
    public List<T> selectAll(String extraCondition) {
        return select(null, extraCondition);
    }

    /**
     * SELECT * FROM TABLE WHERE [whereField whereCondition whereValue](n) [extraCondition]
     *
     * @param whereField
     * @param whereCondition
     * @param whereValue
     * @param extraCondition Optional!
     * @return
     */
    public List<T> selectAll(WhereField whereField, WhereCondition whereCondition, WhereValue whereValue, String extraCondition) {
        return select(null, whereField, whereCondition, whereValue, extraCondition);
    }

    /**
     * SELECT select FROM TABLE [extraCondition]
     *
     * @param select
     * @param extraCondition Optional!
     * @return
     */
    public List<T> select(String select, String extraCondition) {
        return select(select, Where.fields(), Where.conditions(), Where.values(), extraCondition);
    }

    /**
     * SELECT select FROM TABLE WHERE [whereField whereCondition whereValue](n)
     *
     * @param select
     * @param whereField
     * @param whereCondition
     * @param whereValue
     * @param extraCondition Optional!
     * @return
     */
    public List<T> select(String select,
            WhereField whereField, WhereCondition whereCondition, WhereValue whereValue) {
        return select(select, whereField, whereCondition, whereValue, null);
    }

    /**
     * SELECT select FROM TABLE WHERE [whereField whereCondition whereValue](n) [extraCondition]
     *
     * @param select
     * @param whereField
     * @param whereCondition
     * @param whereValue
     * @param extraCondition Optional!
     * @return
     */
    public List<T> select(String select,
            WhereField whereField, WhereCondition whereCondition, WhereValue whereValue,
            String extraCondition) {
        Statement stmt = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List result = new ArrayList();
        if (whereField.getFields().length != whereCondition.getConditions().length) {
            println("Select", "Where params with differente length!");
            showSQL = false;
            return result;
        }
        try {
            StringBuilder sql = new StringBuilder("SELECT ");
            if (select == null) {
                sql.append("*");
            } else {
                sql.append(select);
            }
            sql.append(" FROM ");
            sql.append(tableName);
            if (whereField.getFields().length > 0) {
                concatenateWhere(whereField, sql, whereCondition);
            }
            if (extraCondition != null) {
                sql.append(" ");
                sql.append(extraCondition);
            }
            showSQL(sql);
            if (whereField.getFields().length > 0) {
                prepStmt = conn.prepareStatement(sql.toString());
                int paramsIndex = prepStmt.getParameterMetaData().getParameterCount();
                int colIndex = 1;
                for (int i = 0; i < paramsIndex; i++) {
                    prepStmt.setObject((colIndex++), whereValue.getValues()[i]);
                }
                rs = prepStmt.executeQuery();
            } else {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql.toString());
            }
            if (rs.next()) {
                ResultSetMetaData metaData;
                Object element;
                Field field;
                String columnName;
                try {
                    do {
                        metaData = rs.getMetaData();
                        element = classDB.newInstance();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            columnName = metaData.getColumnName(i);
                            if (columnName.equals(ID)) {
                                field = classDB.getSuperclass().getDeclaredField(columnName);
                            } else {
                                field = classDB.getDeclaredField(columnName);
                            }
                            field.setAccessible(true);
                            if (isSameType(field.getType(), Boolean.class)) {
                                field.set(element, rs.getBoolean(i));
                            } else {
                                field.set(element, rs.getObject(i));
                            }
                        }
                        result.add(element);
                    } while (rs.next());
                } catch (Exception ex) {
                    println("Select-Result", ex.toString());
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            println("select", ex.toString());
        }
        closeSafe(rs);
        closeSafe(stmt);
        closeSafe(prepStmt);
        showSQL = false;
        return result;
    }

    private void closeSafe(AutoCloseable objCloseable) {
        try {
            if (objCloseable != null) {
                objCloseable.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Atualiza o Objeto no banco se ele já existir.
     *
     * @param objDBElement
     * @return False - Se não existir.
     */
    public boolean update(WkTable objDBElement) {
        if (objDBElement == null) {
            return false;
        }
        try {
            String[] upFields = new String[fields.size()];
            Object[] upValues = new Object[fields.size()];
            int index = 0;
            for (Field field : fields.values()) {
                upFields[index] = field.getName();
                upValues[index] = field.get(objDBElement);
                index++;
            }
            return updateByID(Update.fields(upFields),
                    Update.values(upValues),
                    objDBElement.getId());
        } catch (Exception ex) {
            println("update", ex.toString());
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * UPDATE TABLE SET [updateFields](n) = [updateValues](n) WHERE ID = ID
     *
     * @param fields
     * @param values
     * @param id
     * @return
     */
    public boolean updateByID(UpdateField fields, UpdateValue values, int id) {
        Integer update = update(fields, values, Where.fields("id"), Where.conditions("="), Where.values(id));
        return update != null && update == 1;
    }

    /**
     * UPDATE TABLE SET [updateFields](n) = [updateValues](n) WHERE [extraCondition]
     *
     * @param uf
     * @param uv
     * @param extraCondition
     * @return
     */
    public Integer update(UpdateField uf, UpdateValue uv, String extraCondition) {
        return update(uf, uv, Where.fields(), Where.conditions(), Where.values(), extraCondition);
    }

    /**
     * UPDATE TABLE SET [updateFields](n) = [updateValues](n) WHERE [whereField whereCondition whereValue](n)
     *
     * @param uf
     * @param uv
     * @param wf
     * @param wc
     * @param wv
     * @return
     */
    public Integer update(UpdateField uf, UpdateValue uv,
            WhereField wf, WhereCondition wc, WhereValue wv) {
        return update(uf, uv, wf, wc, wv, null);
    }

    /**
     * UPDATE TABLE SET [updateFields](n) = [updateValues](n) WHERE [whereField whereCondition whereValue](n)
     * [extraCondition]
     *
     * @param uf
     * @param uv
     * @param wf
     * @param wc
     * @param wv
     * @return
     */
    public Integer update(UpdateField uf, UpdateValue uv,
            WhereField wf, WhereCondition wc, WhereValue wv,
            String extraCondition) {
        if (uf.getFields().length != uv.getValues().length) {
            println("Update", "UpdateField/UpdateValue params with differente length!");
            showSQL = false;
            return null;
        }
        if (wf.getFields().length != wc.getConditions().length) {
            println("Update", "Where params with differente length!");
            showSQL = false;
            return null;
        }
        try {
            StringBuilder sql = new StringBuilder("UPDATE ");
            sql.append(tableName);
            sql.append(" SET ");
            int index = 0;
            for (String colN : uf.getFields()) {
                sql.append(colN);
                // Teste para saber se é instrução de soma ou diminuição (não é válido para campo TEXT)
                if (uv.getValues()[index] instanceof String
                        && (uv.getValues()[index].toString().contains("+")
                        || uv.getValues()[index].toString().contains("-"))
                        && fields.get(colN).getType() != String.class) {
                    sql.append(" = ");
                    sql.append(uv.getValues()[index]);
                } else {
                    sql.append(" = ?");
                }
                if ((++index) < uf.getFields().length) {
                    sql.append(", ");
                } else {
                    sql.append(" ");
                }
            }
            if (wf.getFields().length > 0) {
                concatenateWhere(wf, sql, wc);
            }
            if (extraCondition != null) {
                sql.append(extraCondition);
            }
            showSQL(sql);
            ps = conn.prepareStatement(sql.toString());
            index = 0;
            for (Object value : uv.getValues()) {
                // Teste para saber se é instrução de soma ou diminuição (não é válido para campo TEXT)
                if (value instanceof String
                        && (value.toString().contains("+")
                        || value.toString().contains("-"))
                        && fields.get(uf.getFields()[index]).getType() != String.class) {
                    continue;
                }
                setPrepareStatementValue((++index), value);
            }
            if (wf.getFields().length > 0) {
                for (int i = 0; i < wv.getValues().length; i++) {
                    setPrepareStatementValue((++index), wv.getValues()[i]);
                }
            }
            return ps.executeUpdate();
        } catch (Exception ex) {
            println("Update", ex.toString());
            ex.printStackTrace();
        } finally {
            // Pois retorna do método dentro do try/catch
            closeSafe(ps);
            showSQL = false;
        }
        return null;
    }

    public List<Map<String, Object>> executeQuery(String query) {
        // Lista dos registros: "campo":"valor"
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            showSQL(query);
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                ResultSetMetaData metaData;
                Map<String, Object> map;
                int columnCount;
                do {
                    metaData = rs.getMetaData();
                    columnCount = metaData.getColumnCount();
                    map = new HashMap<>(columnCount);
                    for (int i = 1; i <= columnCount; i++) {
                        map.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    list.add(map);
                } while (rs.next());
            }
        } catch (Exception ex) {
            println("Query-Result", ex.toString());
            ex.printStackTrace();
        } finally {
            closeSafe(rs);
            closeSafe(stmt);
            showSQL = false;
        }
        return list;
    }

    public boolean deleteByID(Integer id) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("delete from " + tableName + " where id = " + id);
            return true;
        } catch (Exception ex) {
            println("Delete-Result", ex.toString());
            ex.printStackTrace();
        } finally {
            closeSafe(stmt);
            showSQL = false;
        }
        return false;
    }

    private void setPrepareStatementValue(int parameterIndex, Object x) throws SQLException {
        if (showSQL) {
            System.out.printf("[SQL](param-%d)='%s'\n", parameterIndex, x.toString());
        }
        ps.setObject(parameterIndex, x);
    }

    private void concatenateWhere(WhereField wf, StringBuilder sql, WhereCondition wc) {
        int index = wf.getFields().length;
        int condIndex = 0;
        sql.append(" WHERE ");
        for (String whereF : wf.getFields()) {
            sql.append(whereF);
            sql.append(" ");
            sql.append(wc.getConditions()[condIndex]);
            // Se for condição 'entre'
            if (wc.getConditions()[condIndex].compareToIgnoreCase("between") == 0) {
                sql.append(" ? and ? ");
            } else {
                sql.append(" ? ");
            }
            if ((--index) > 0) {
                sql.append("and ");
            }
            condIndex++;
        }
    }

    private void showSQL(StringBuilder sql) {
        if (showSQL && sql != null) {
            println(sql.toString());
        }
    }

    private void showSQL(String sql) {
        if (showSQL && sql != null) {
            println(sql);
        }
    }

    private void println(String msg) {
        println(null, msg);
    }

    private void println(String cmd, String msg) {
        if (cmd == null) {
            System.out.printf("[SQL][%s] %s\n", tableName, msg);
        } else {
            System.out.printf("[SQL][%s][%s] %s\n", tableName, cmd, msg);
        }
    }
}

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

//Compile "java Main"
public class Main {
    static int op = 0;
    static Connection conn = null;
    static String url = "jdbc:sqlite:C:/Users/leome/IdeaProjects/Project/src/bills.db";
    static ResultSet result = null;
    static ArrayList<Integer> Id = new ArrayList<>(), Price = new ArrayList<>(), Day = new ArrayList<>(),
            Month = new ArrayList<>(), Year = new ArrayList<>();
    static ArrayList<String > Description = new ArrayList<>(), Alumn = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

/*
Base de datos: mydatabase
tabla: newTable
conexion: sqlite3 bills.sb
SELECT * FROM bill;
 */
    static void Menu(){
        System.out.println("\tMENU");
        System.out.println("1. Consulter Documents");
        System.out.println("2. Add Documents");
        System.out.println("3. Exit");
        System.out.print("Select option: ");
        op = scanner.nextInt();
    }

    /**
     * connection with database
     */
    static void connect(){
        try{
            conn = DriverManager.getConnection(url);
            System.out.println("Database connected. . .");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    static void consult(){
        try{
            PreparedStatement query = conn.prepareStatement("SELECT * FROM bill");
            result = query.executeQuery();

            Id.clear();
            Description.clear();
            Price.clear();
            Day.clear();
            Month.clear();
            Year.clear();
            Alumn.clear();

            while (result.next()) {
                Id.add(result.getInt("id"));
                Description.add(result.getString("decription"));
                Price.add(result.getInt("price"));
                Day.add(result.getInt("day"));
                Month.add(result.getInt("month"));
                Year.add(result.getInt("year"));
                Alumn.add(result.getString("alumno"));

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n ID | DESCRIPTION | PRICE |    DATE    |  ALUMNO");
        for(int i = 0; i < Id.size(); i++){
                System.out.printf("%03d |  %s  |  %3d  | %02d-%02d-%d | %s\n",
                        Id.get(i), Description.get(i), Price.get(i), Day.get(i), Month.get(i),Year.get(i), Alumn.get(i));

        }
        System.out.println("\n");
    }

    static void consultMenu(){
        System.out.println("1.Buscar por mes y año");
        System.out.println("2.Buscar por id de factura");
        System.out.println("3. Exit");
        System.out.print("Select option: ");
        op = scanner.nextInt();
    }

    static void searchYear(int month, int year){
        int total=0;
        String sql = "SELECT * FROM bill WHERE MONTH = " + String.valueOf(month) + " AND YEAR = " + String.valueOf(year);
        try{
            PreparedStatement query = conn.prepareStatement(sql);
            result = query.executeQuery();

            Id.clear();
            Description.clear();
            Price.clear();
            Day.clear();
            Month.clear();
            Year.clear();
            Alumn.clear();

            while (result.next()) {
                Id.add(result.getInt("id"));
                Description.add(result.getString("decription"));
                Price.add(result.getInt("price"));
                Day.add(result.getInt("day"));
                Month.add(result.getInt("month"));
                Year.add(result.getInt("year"));
                Alumn.add(result.getString("alumno"));

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n ID | DESCRIPTION | PRICE |    DATE    |  ALUMNO");
        for(int i = 0; i < Id.size(); i++){
            System.out.printf("%03d |  %s  |  %3d  | %02d-%02d-%d | %s\n",
                    Id.get(i), Description.get(i), Price.get(i), Day.get(i), Month.get(i),Year.get(i), Alumn.get(i));
        }
        System.out.println("\n");
        //Total pagado por mes
        for(int i = 0; i < Price.size(); i++){
            total += Price.get(i);
        }
        System.out.println("El total del mes es: " + total);
    }
    static void searchID(int id){
        int total=0;
        String sql = "SELECT * FROM bill WHERE ID = " + String.valueOf(id);
        try{
            PreparedStatement query = conn.prepareStatement(sql);
            result = query.executeQuery();

            Id.clear();
            Description.clear();
            Price.clear();
            Day.clear();
            Month.clear();
            Year.clear();
            Alumn.clear();

            while (result.next()) {
                Id.add(result.getInt("id"));
                Description.add(result.getString("decription"));
                Price.add(result.getInt("price"));
                Day.add(result.getInt("day"));
                Month.add(result.getInt("month"));
                Year.add(result.getInt("year"));
                Alumn.add(result.getString("alumno"));

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n ID | DESCRIPTION | PRICE |    DATE    |  ALUMNO");
        for(int i = 0; i < Id.size(); i++){
            System.out.printf("%03d |  %s  |  %3d  | %02d-%02d-%d | %s\n",
                    Id.get(i), Description.get(i), Price.get(i), Day.get(i), Month.get(i),Year.get(i), Alumn.get(i));
        }
        System.out.println("\n");
    }

    static void insert(int id, String description, int price, int day, int month, int year, String name){
        String sql = "INSERT INTO bill(id,decription,price,day,month,year,alumno) VALUES(" + String.valueOf(id) + ",'" + description +"'," + String.valueOf(price)+
                "," + String.valueOf(day)+","+String.valueOf(month)+","+String.valueOf(year)+",'"+name+"')";
        try{
            PreparedStatement query = conn.prepareStatement(sql);
            //query.execute();
            System.out.println(sql);
            System.out.println("Datos guardados");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n");
    }

    /**
     * Close database
     */
    static void close(){
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database closed. . .");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args){

        connect();
        while (op != 3) {
            Menu();
            switch (op) {
                case 1:
                    consult();
                    while (op != 3){
                        consultMenu();
                        switch (op){
                            case 1:
                                int month, year;
                                System.out.print("Ingresar mes de busqueda: ");
                                month = scanner.nextInt();
                                System.out.print("Ingresar año de busqueda: ");
                                year = scanner.nextInt();
                                searchYear(month,year);
                                break;
                            case 2:
                                int id;
                                System.out.print("Ingresar el ID de la factura");
                                id = scanner.nextInt();
                                searchID(id);
                                break;
                            default:
                                System.out.println("No es una option");
                        }
                    }

                    break;
                case 2:
                    int id, price, day, month, year;
                    String description, buffer, name;
                    System.out.print("ID: ");
                    id = scanner.nextInt();
                    buffer = scanner.nextLine(); // clean buffer
                    System.out.print("Description (1.Matricula 2.Mensualidad): ");
                    op = scanner.nextInt();
                    if(op != 1 && op != 2){
                        System.out.println("Opcion incorrecta");
                        break;
                    }else{
                        if(op == 1)
                            description = "MATRICULA";
                        else
                            description = "MENSUALIDAD";
                    }
                    System.out.print("Price: ");
                    price = scanner.nextInt();
                    System.out.print("Day: ");
                    day = scanner.nextInt();
                    if(day > 31 || day < 1){
                        System.out.println("Datos Incorrectos");
                        break;
                    }
                    System.out.print("Month: ");
                    month = scanner.nextInt();
                    if(month > 12 || month < 1){
                        System.out.println("Datos Incorrectos");
                        break;
                    }
                    System.out.print("Year: ");
                    year = scanner.nextInt();
                    buffer = scanner.nextLine(); // clean buffer
                    System.out.println("Ingresar el nombre del alumno");
                    name = scanner.nextLine();
                    insert(id,description,price,day,month,year,name);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("No es una option");
            }
        }
        close();
    }
}

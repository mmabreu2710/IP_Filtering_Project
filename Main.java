public class Main{
    public static auxClass auxClassGui;
      public static void main(String[] args) {
          auxClassGui= new auxClass();
          auxClassGui.setupDatabase(); // sets a database directory with several subdirectories and defining xml files
          auxClassGui.readDatabase(); //reads all information from database
          //Create GUI
          GUI gui = new GUI(auxClassGui, auxClassGui.getBasePath(), auxClassGui.getDomainList(),"Choose Authorized Domains by Device");

        }
    
}
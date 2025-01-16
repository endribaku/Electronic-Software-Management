package DAO;

import Models.Sector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class SectorFileHandler {
    public static final String FILE_PATH = "Project/Data/sectors.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<Sector> sectors = FXCollections.observableArrayList();

    public ObservableList<Sector> getAllUsers() {
        if(sectors.isEmpty()) {
            selectAllSectors();
        }
        return sectors;
    }

    public void insertSector(Sector sector) {
        try(FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);
            writer.writeObject(sector);
        } catch(IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteSector(Sector sector) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            sectors.remove(sector);
            for (Sector s : sectors) {
                outputStream.writeObject(s);
            }
        } catch (EOFException eofe) {

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

        public void deleteAll(ArrayList<Sector> sectorsToRemove) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            for(Sector s : sectorsToRemove) {
                if (sectors.containsAll(sectorsToRemove)) {
                    sectors.removeAll(sectorsToRemove);
                } else if (sectors.contains(s)) {
                    sectors.remove(s);
                }
            }
            for(Sector s : sectors) {
                outputStream.writeObject(s);
            }
        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    public boolean updateAll() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for(Sector s : sectors) {
                outputStream.writeObject(s);
            }
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public Sector selectSector(String sectorName){
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Sector sector;
            while(true) {
                sector = (Sector) reader.readObject();
                if(sector.getSectorName().equals(sectorName))
                    return sector;
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void selectAllSectors() {
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Sector sector;
            while(true) {
                sector = (Sector) reader.readObject();
                sectors.add(sector);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

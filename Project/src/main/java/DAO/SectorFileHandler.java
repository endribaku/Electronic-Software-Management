package DAO;

import Interfaces.DAO.ISectorFileHandler;
import Models.Sector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class SectorFileHandler implements ISectorFileHandler {

    public static final String FILE_PATH = "Project/Data/sectors.dat";

    private final File dataFile;

    private final ObservableList<Sector> sectors = FXCollections.observableArrayList();

    public SectorFileHandler() {
        this(new File(FILE_PATH));
    }

    public SectorFileHandler(File dataFile) {
        this.dataFile = dataFile;
    }

    public ObservableList<Sector> getAllUsers() {
        if (sectors.isEmpty()) {
            selectAllSectors();
        }
        return sectors;
    }

    public void insertSector(Sector sector) {
        try (FileOutputStream outputStream = new FileOutputStream(dataFile, true)) {
            ObjectOutputStream writer;
            if (dataFile.length() > 0)
                writer = new HeaderlessObjectOutputStream(outputStream);
            else
                writer = new ObjectOutputStream(outputStream);

            writer.writeObject(sector);
            sectors.add(sector);

        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public void deleteSector(Sector sector) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            sectors.remove(sector);
            for (Sector s : sectors) {
                outputStream.writeObject(s);
            }
        } catch (EOFException eofe) {

        } catch (IOException exception) {
            exception.getMessage();
        }
    }

    public void deleteAll(ArrayList<Sector> sectorsToRemove) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (Sector s : sectorsToRemove) {
                if (sectors.containsAll(sectorsToRemove)) {
                    sectors.removeAll(sectorsToRemove);
                } else if (sectors.contains(s)) {
                    sectors.remove(s);
                }
            }
            for (Sector s : sectors) {
                outputStream.writeObject(s);
            }
        } catch (IOException exception) {
            exception.getMessage();
        }
    }

    public boolean updateAll() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (Sector s : sectors) {
                outputStream.writeObject(s);
            }
            return true;
        } catch (IOException exception) {
            exception.getMessage();
            return false;
        }
    }

    public Sector selectSector(String sectorName) {
        if (!dataFile.exists()) {
            return null;
        }

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            Sector sector;
            while (true) {
                sector = (Sector) reader.readObject();
                if (sector.getSectorName().equals(sectorName))
                    return sector;
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public void selectAllSectors() {
        sectors.clear();

        if (!dataFile.exists()) {
            return;
        }

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataFile))) {
            Sector sector;
            while (true) {
                sector = (Sector) reader.readObject();
                sectors.add(sector);
            }
        }
        catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}

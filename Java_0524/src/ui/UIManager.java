package ui;

import controller.*;
import service.*;
import manager.*;
import repository.UserPlantDataRepository;

import javax.swing.*;

public class UIManager {

    private final JFrame frame;

    private final UserController userController;
    private final UserPlantDatacontroller userPlantDataController;
    private final PlantManagementController plantManagementController;

    public UserController getUserController() {
        return userController;
    }

    public UserPlantDatacontroller getUserPlantDataController() {
        return userPlantDataController;
    }

    public PlantManagementController getplantManagementController() {
        return plantManagementController;
    }

    public UIManager(JFrame frame) {
        this.frame = frame;

        // 1. Repository
        UserPlantDataRepository userPlantDataRepository = new UserPlantDataRepository();

        // 2. Manager
        UserPlantDataManager userPlantDataManager = new UserPlantDataManager(userPlantDataRepository);
        UserManager userManager = new UserManager();

        // 3. Services
        PlantCareService plantCareService = new PlantCareService(userManager, userPlantDataManager);
        PlantQueryService plantQueryService = new PlantQueryService(userManager, userPlantDataManager);

        // 4. Controllersa
        this.userController = new UserController(userManager, userPlantDataManager);
        this.userPlantDataController = new UserPlantDatacontroller(
            plantCareService,
            plantQueryService,
            userPlantDataManager,
            userManager
        );
        this.plantManagementController = new PlantManagementController(userManager, userPlantDataManager);
    }

    public void showScreen(JPanel screen) {
        frame.setContentPane(screen);
        frame.revalidate();
        frame.repaint();
    }
}

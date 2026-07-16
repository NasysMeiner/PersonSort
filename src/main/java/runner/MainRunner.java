package runner;

import application.DataPersister;
import java.io.IOException;
import model.DataBaseService;
import router.MenuType;
import router.Router;
import ui.View;

public class MainRunner {
    private final DataPersister dataPersister;
    private final DataBaseService dataBaseService;
    private final View view;
    private final Router router;

    public MainRunner(DataPersister dataPersister, 
        DataBaseService dataBaseService, 
        View view, 
        Router router
    ) {
        this.dataPersister = dataPersister;
        this.dataBaseService = dataBaseService;
        this.view = view;
        this.router = router;
    }

    public void run() {
        try {
            router.run(MenuType.START_LOAD_MENU);
        } catch (Exception e) {
            view.showMessage(e.getMessage());
            return;
        }

        if(dataBaseService.getSize() > 0) {
            try {
                String path = dataPersister.saveSystem();
                view.showMessage("State save in: " + path + "\n\nEnd programm!");
            } catch (IOException e) {
                view.showMessage(e.getMessage());
            }
        }
    }
}

package maalausprojektikirjanpito.ui.panes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import maalausprojektikirjanpito.domain.Layer;
import maalausprojektikirjanpito.domain.ManagerService;
import maalausprojektikirjanpito.domain.Surface;
import maalausprojektikirjanpito.domain.SurfaceTreatment;
import static maalausprojektikirjanpito.domain.Utilities.stringLengthCheck;


public class SurfacePane extends BorderPane {
    public BorderPane surfacePane;
    private TreeView layersTree;
    private Surface currentlyViewedSurface;
    private ManagerService service;
    
    // goToNewLayerTreatmentButtonBox
    private Button goToCreateNewLayerButton;
    private Button goToCreateNewTreatmentButton;
    private VBox goToNewLayerTreatmentButtonBox;
    
    // createNewLayerPane
    private TextField layerNameInputBox;
    private Label createNewLayerNameLabel;
    private ComboBox<SurfaceTreatment> layerTreatmentSelectionBoxOne;
    private ComboBox<SurfaceTreatment> layerTreatmentSelectionBoxTwo;
    private ComboBox<SurfaceTreatment> layerTreatmentSelectionBoxThree;
    private ComboBox<SurfaceTreatment> layerTreatmentSelectionBoxFour;
    private VBox treatmentsSelectionBox;
    private Label createNewLayerNoteLabel;
    private TextField createNewLayerNoteInput;
    private Label createNewLayerMessageLabel;
    private Button createNewLayerButton;
    private Button cancelNewLayerButton;
    private HBox createNewLayerButtonBox;
    private GridPane createNewLayerPane;
    
    // createNewTreatmentPane
    Label createNewTreatmentHeaderLabel;
    Label createNewTreatmentNameLabel;
    TextField createNewTreatmentNameInput;
    Label createNewTreatmentTypeLabel;
    ComboBox<String> createNewTreatmentTypeInputBox;
    Label createNewTreatmentManufacturerLabel;
    ComboBox<String> createNewTreatmentManufacturerInputBox;
    Label createNewTreatmentColourLabel;
    ColorPicker createNewTreatmentColourPicker;
    Button createNewTreatmentButton;
    Button cancelNewTreatmentButton;
    HBox createNewTreatmentButtonBox;
    Label createNewTreatmentMessage;
    GridPane createNewTreatmentPane;
    

    public SurfacePane(ManagerService service) {
        this.service = service;
        this.currentlyViewedSurface = this.service.getCurrentlyViewedSurface();
        
        this.layersTree = new TreeView();
        
        // goToNewLayerTreatmentButtonBox
        goToCreateNewLayerButton = new Button("Create or edit a layer");
        goToCreateNewTreatmentButton = new Button("Create a new treatment");
        goToNewLayerTreatmentButtonBox = new VBox(goToCreateNewLayerButton, goToCreateNewTreatmentButton);
        goToNewLayerTreatmentButtonBox.setSpacing(6);
        GridPane.setMargin(goToNewLayerTreatmentButtonBox, new Insets(6,0,0,0));
        
        // createNewLayerPane
        createNewLayerNameLabel = new Label("Layer name");
        layerNameInputBox = new TextField();
        layerNameInputBox.setStyle("-fx-font-size:10");
        layerTreatmentSelectionBoxOne = new ComboBox();
        layerTreatmentSelectionBoxOne.setCellFactory(param -> new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxOne.setButtonCell(new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxOne.setStyle("-fx-font-size:10;");
        layerTreatmentSelectionBoxTwo = new ComboBox();
        layerTreatmentSelectionBoxTwo.setCellFactory(param -> new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxTwo.setButtonCell(new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxTwo.setStyle("-fx-font-size:10");
        layerTreatmentSelectionBoxThree = new ComboBox();
        layerTreatmentSelectionBoxThree.setCellFactory(param -> new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxThree.setButtonCell(new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxThree.setStyle("-fx-font-size:10");
        layerTreatmentSelectionBoxFour = new ComboBox();
        layerTreatmentSelectionBoxFour.setCellFactory(param -> new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxFour.setButtonCell(new SurfaceTreatmentCellFactory());
        layerTreatmentSelectionBoxFour.setStyle("-fx-font-size:10");
        treatmentsSelectionBox = new VBox(layerTreatmentSelectionBoxOne, layerTreatmentSelectionBoxTwo, layerTreatmentSelectionBoxThree, layerTreatmentSelectionBoxFour);
        treatmentsSelectionBox.setSpacing(3);
        createNewLayerNoteLabel = new Label("Note");
        createNewLayerNoteInput = new TextField();
        createNewLayerNoteInput.setStyle("-fx-font-size:10");
        createNewLayerMessageLabel = new Label();
        createNewLayerMessageLabel.setTextFill(Color.RED);
        createNewLayerMessageLabel.setWrapText(true);
        createNewLayerMessageLabel.setMinHeight(26);
        createNewLayerButton = new Button("Create");
        cancelNewLayerButton = new Button("Cancel");
        createNewLayerButtonBox = new HBox(createNewLayerButton, cancelNewLayerButton, createNewLayerMessageLabel);
        createNewLayerButtonBox.setSpacing(6);
        GridPane.setColumnSpan(createNewLayerButtonBox, 3);
        createNewLayerPane = new GridPane();
        createNewLayerPane.add(createNewLayerNameLabel, 0, 0);
        createNewLayerPane.add(layerNameInputBox, 0, 1);
        createNewLayerPane.add(treatmentsSelectionBox, 0, 2);
        createNewLayerPane.add(createNewLayerNoteLabel, 0, 3);
        createNewLayerPane.add(createNewLayerNoteInput, 0, 4);
        createNewLayerPane.add(createNewLayerButtonBox, 0, 5);
        createNewLayerPane.setVgap(6);
        createNewLayerPane.setHgap(6);
        
        // createNewTreatmentPane
        createNewTreatmentHeaderLabel = new Label("Create a new surface treatment");
        createNewTreatmentNameLabel = new Label("Treatment name");
        createNewTreatmentNameInput = new TextField();
        createNewTreatmentNameInput.setStyle("-fx-font-size:10");
        createNewTreatmentTypeLabel = new Label("Type");
        createNewTreatmentTypeInputBox = new ComboBox();
        createNewTreatmentTypeInputBox.setValue("");
        createNewTreatmentTypeInputBox.setStyle("-fx-font-size:10");
        createNewTreatmentTypeInputBox.setEditable(true);
        createNewTreatmentManufacturerLabel = new Label("Manufacturer");
        createNewTreatmentManufacturerInputBox = new ComboBox();
        createNewTreatmentManufacturerInputBox.setValue("");
        createNewTreatmentManufacturerInputBox.setStyle("-fx-font-size:10");
        createNewTreatmentManufacturerInputBox.setEditable(true);
        createNewTreatmentColourLabel = new Label("Colour");
        createNewTreatmentColourPicker = new ColorPicker();
        createNewTreatmentColourPicker.setStyle("-fx-font-size:10");
        createNewTreatmentButton = new Button("Create");
        cancelNewTreatmentButton = new Button("Cancel");
        createNewTreatmentButtonBox = new HBox(createNewTreatmentButton, cancelNewTreatmentButton);
        createNewTreatmentButtonBox.setSpacing(6);
        createNewTreatmentMessage = new Label("");
        createNewTreatmentMessage.setTextFill(Color.RED);
        GridPane.setColumnSpan(createNewTreatmentMessage, 3);
        createNewTreatmentPane = new GridPane();
        createNewTreatmentPane.add(createNewTreatmentHeaderLabel, 0, 0);
        createNewTreatmentPane.add(createNewTreatmentNameLabel, 0, 1);
        createNewTreatmentPane.add(createNewTreatmentNameInput, 0, 2);
        createNewTreatmentPane.add(createNewTreatmentTypeLabel, 1, 1);
        createNewTreatmentPane.add(createNewTreatmentTypeInputBox, 1, 2);
        createNewTreatmentPane.add(createNewTreatmentManufacturerLabel, 2, 1);
        createNewTreatmentPane.add(createNewTreatmentManufacturerInputBox, 2, 2);
        createNewTreatmentPane.add(createNewTreatmentColourLabel, 3, 1);
        createNewTreatmentPane.add(createNewTreatmentColourPicker, 3, 2);
        createNewTreatmentPane.add(createNewTreatmentButtonBox, 0, 3);
        createNewTreatmentPane.add(createNewTreatmentMessage, 1, 3);
        createNewTreatmentPane.setVgap(6);
        createNewTreatmentPane.setHgap(6);
        
        // Layout for SurfacePane
        surfacePane = new BorderPane();
        surfacePane.setCenter(this.layersTree);
        surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
        BorderPane.setMargin(this.layersTree, new Insets(0, 0, 12, 0));
        BorderPane.setMargin(surfacePane, new Insets(0, 0, 0, 20));
        
        // Button actions for SurfacePane
        goToCreateNewLayerButton.setOnAction(event -> {
            if (this.currentlyViewedSurface.getSubprojectId() != -1) {
                surfacePane.setBottom(createNewLayerPane);
            }
        });
        goToCreateNewTreatmentButton.setOnAction(event -> {
            if (this.currentlyViewedSurface.getSubprojectId() != -1) {
                surfacePane.setBottom(createNewTreatmentPane);
            }
        });
        createNewLayerButton.setOnAction(event -> {
            String newLayerName = layerNameInputBox.getText().trim();
            String newLayerNote = createNewLayerNoteInput.getText().trim();
            ArrayList<Integer> treatmentsToAdd = new ArrayList();
            if (this.layerTreatmentSelectionBoxOne.getValue() != null) {
                treatmentsToAdd.add(this.layerTreatmentSelectionBoxOne.getValue().getTreatmentId());
            }
            if (this.layerTreatmentSelectionBoxTwo.getValue() != null) {
                treatmentsToAdd.add(this.layerTreatmentSelectionBoxTwo.getValue().getTreatmentId());
            }
            if (this.layerTreatmentSelectionBoxThree.getValue() != null) {
                treatmentsToAdd.add(this.layerTreatmentSelectionBoxThree.getValue().getTreatmentId());
            }
            if (this.layerTreatmentSelectionBoxFour.getValue() != null) {
                treatmentsToAdd.add(this.layerTreatmentSelectionBoxFour.getValue().getTreatmentId());
            }
//            System.out.println("treatmentIds: " + firstTreatmentId + secondTreatmentId + thirdTeatmentId + fourthTreatmentId);
            if (!(stringLengthCheck(newLayerName, 3, 40) && stringLengthCheck(newLayerNote, 0, 60))) {
                createNewLayerMessageLabel.setText("Layer name must be 3-40 and layer note must be 0-60 characters long");
            } else if (!(newLayerName.matches("[A-Za-z0-9\\s]*") && newLayerNote.matches("[A-Za-z0-9\\s]*"))) {
                createNewLayerMessageLabel.setText("Layer name and note must only contain numbers and letters");
            } else {
                Layer createdLayer = this.service.createLayer(newLayerName, newLayerNote);
                this.service.addLayerToSurface(this.currentlyViewedSurface.getSurfaceId(), createdLayer.getLayerId());
                treatmentsToAdd.forEach(t -> this.service.addTreatmentToLayer(createdLayer.getLayerId(), t));
                this.layerNameInputBox.setText("");
                this.createNewLayerNoteInput.setText("");
                this.createNewLayerMessageLabel.setText("");
                this.refresh();
                this.surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
            }
        });
        createNewTreatmentButton.setOnAction(event -> {
            String newTreatmentName = createNewTreatmentNameInput.getText().trim();
            String newTreatmentType = createNewTreatmentTypeInputBox.getValue().trim();
            String newTreatmentManufacturer = createNewTreatmentManufacturerInputBox.getValue().trim();
            Paint newTreatmentColour = createNewTreatmentColourPicker.getValue();
            if (!(stringLengthCheck(newTreatmentName, 3, 40) && stringLengthCheck(newTreatmentType, 3, 40) && stringLengthCheck(newTreatmentManufacturer, 3, 40))) {
                createNewTreatmentMessage.setText("Treatment name, type and manufaturer must be 3-40 characters long");
            } else if (!(newTreatmentName.matches("[A-Za-z0-9\\s]*") && newTreatmentType.matches("[A-Za-z0-9\\s]*") && newTreatmentManufacturer.matches("[A-Za-z0-9\\s]*"))) {
                createNewTreatmentMessage.setText("Treatment name, type and manufaturer must only contain numbers and letters");
            } else {
                this.service.createTreatment(newTreatmentName, newTreatmentType, newTreatmentManufacturer, newTreatmentColour);
                createNewTreatmentNameInput.setText("");
                createNewTreatmentTypeInputBox.setValue("");
                createNewTreatmentManufacturerInputBox.setValue("");
                createNewTreatmentColourPicker.setValue(Color.WHITE);
                createNewTreatmentMessage.setText("");
                this.refresh();
                surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
            }
        });
        cancelNewLayerButton.setOnAction(event -> {
            this.layerNameInputBox.setText("");
            this.createNewLayerNoteInput.setText("");
            this.createNewLayerMessageLabel.setText("");
            this.surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
        });
        cancelNewTreatmentButton.setOnAction(event -> {
            this.createNewTreatmentNameInput.setText("");
            this.createNewTreatmentTypeInputBox.setValue("");
            this.createNewTreatmentManufacturerInputBox.setValue("");
            this.createNewTreatmentColourPicker.setValue(Color.WHITE);
            this.createNewTreatmentMessage.setText("");
            surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
        });
        
        
    }

    public BorderPane getSurfacePane() {
        return surfacePane;
    }

    public void refresh() {
        this.currentlyViewedSurface = this.service.getCurrentlyViewedSurface();
        ArrayList<Layer> updatedLayers = this.service.fetchLayers(this.currentlyViewedSurface);
        this.currentlyViewedSurface.setLayers(updatedLayers);
        this.layersTree.setRoot(this.getLayerTreeItems(this.currentlyViewedSurface.getSurfaceName(), updatedLayers));
//        this.layerNameInputBox.getItems().setAll(updatedLayers.stream().map(l -> l.getLayerName()).collect(Collectors.toList()));
        ObservableList<SurfaceTreatment> updatedTreatments = FXCollections.observableArrayList();
        this.service.fetchAllSurfaceTreatments().forEach(st -> updatedTreatments.add(st));
        this.layerTreatmentSelectionBoxOne.setItems(updatedTreatments);
        this.layerTreatmentSelectionBoxTwo.setItems(updatedTreatments);
        this.layerTreatmentSelectionBoxThree.setItems(updatedTreatments);
        this.layerTreatmentSelectionBoxFour.setItems(updatedTreatments);
        this.createNewTreatmentTypeInputBox.getItems().setAll(surfaceTreatmentTypesAsStrings(updatedTreatments));
        this.createNewTreatmentManufacturerInputBox.getItems().setAll(this.surfaceTreatmentManufacturersAsStrings(updatedTreatments));
    }
    
    public TreeItem getLayerTreeItems(String surface, ArrayList<Layer> layers) {
        TreeItem newRootItem = new TreeItem(surface);
        newRootItem.setExpanded(true);
        ArrayList<TreeItem> treeItems = new ArrayList<>();
        layers.stream().forEach(layer -> {
            treeItems.add(this.layerNodeAsTreeItem(layer));
        });
        newRootItem.getChildren().addAll(treeItems);
        return newRootItem;
    }
    
    public TreeItem layerNodeAsTreeItem(Layer layer) {
        TreeItem treeItemToReturn = new TreeItem();
//        HBox layerColoursBox = new HBox();
        Label layerNameLabel = new Label(layer.getLayerName() + ":");
        for (SurfaceTreatment st : layer.getTreatments()) {
//            layerColoursBox.getChildren().add(new Circle(6, st.getTreatmentColour()));
            treeItemToReturn.getChildren().add(new TreeItem(this.surfaceTreatmentAsNode(st)));
        }
        Label layerNoteLabel = new Label(layer.getLayerNote());
        layerNoteLabel.setMaxWidth(240);
        layerNoteLabel.setWrapText(true);
        HBox layerBox = new HBox(layerNameLabel, layerNoteLabel);
        layerBox.setSpacing(6);
        treeItemToReturn.setValue(layerBox);
        treeItemToReturn.setExpanded(true);
        return treeItemToReturn;
    }
    
    public Node surfaceTreatmentAsNode(SurfaceTreatment surfaceTreatment) {
        Label treatmentNameLabel = new Label(surfaceTreatment.getTreatmentName());
        treatmentNameLabel.setStyle("-fx-font-weight: bold;");
        Circle treatmentColourCircle = new Circle(5, surfaceTreatment.getTreatmentColour());
        Label treatmentManufacturerLabel = new Label(surfaceTreatment.getTreatmentManufacturer());
        Label treatmentTypeLabel = new Label(surfaceTreatment.getTreatmentType());
        HBox treatmentBox = new HBox(treatmentNameLabel, treatmentColourCircle, treatmentTypeLabel, treatmentManufacturerLabel);
        treatmentBox.setId(surfaceTreatment.getTreatmentId().toString());
        treatmentBox.setSpacing(4);
//        System.out.println(treatmentBox.getId() + treatmentNameLabel.getText() + treatmentColourCircle.toString() + treatmentManufacturerLabel.getText() + treatmentTypeLabel.getText());
        return treatmentBox;
    }
    
        
    public ObservableList<String> surfaceTreatmentTypesAsStrings(ObservableList<SurfaceTreatment> treatments) {
        ObservableList<String> types = FXCollections.observableArrayList();
        ArrayList<String> treatmentsAsString = (ArrayList<String>) treatments.stream().map(t -> t.getTreatmentType()).collect(Collectors.toList());
        for (String treatmentType : treatmentsAsString) {
            if (!types.contains(treatmentType)) {
                types.add(treatmentType);
            }
        }
        return types;
    }
    
    public ObservableList<String> surfaceTreatmentManufacturersAsStrings(ObservableList<SurfaceTreatment> treatments) {
        ObservableList<String> manufacturers = FXCollections.observableArrayList();
        ArrayList<String> manufacturersAsString = (ArrayList<String>) treatments.stream().map(t -> t.getTreatmentManufacturer()).collect(Collectors.toList());
        for (String manufacturer : manufacturersAsString) {
            if (!manufacturers.contains(manufacturer)) {
                manufacturers.add(manufacturer);
            }
        }
        return manufacturers;
    }
    
    class SurfaceTreatmentCellFactory extends ListCell<SurfaceTreatment> {
        @Override
        protected void updateItem(SurfaceTreatment surfaceTreatment, boolean empty) {
            super.updateItem(surfaceTreatment, empty);
            if (surfaceTreatment == null || empty)
                setGraphic(null);
            else {
                Label treatmentNameLabel = new Label(surfaceTreatment.getTreatmentName());
                treatmentNameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                Circle treatmentColourCircle = new Circle(6, surfaceTreatment.getTreatmentColour());
                Label treatmentManufacturerLabel = new Label(surfaceTreatment.getTreatmentManufacturer());
                treatmentManufacturerLabel.setStyle("-fx-text-fill: black;");
                Label treatmentTypeLabel = new Label(surfaceTreatment.getTreatmentType());
                treatmentTypeLabel.setStyle("-fx-text-fill: black;");
                HBox treatmentBox = new HBox(treatmentNameLabel, treatmentColourCircle, treatmentTypeLabel, treatmentManufacturerLabel);
                treatmentBox.setId(surfaceTreatment.getTreatmentId().toString());
                treatmentBox.setSpacing(4);
                setGraphic(treatmentBox);
            }
            setText("");
        }
    }
}

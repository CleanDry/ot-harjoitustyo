package maalausprojektikirjanpito.ui.panes;

import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private ComboBox<String> layerNameInputBox;
    private Label createNewLayerNameLabel;
    private ComboBox<Node> layerTreatmentSelectionBoxOne;
    private ComboBox<Node> layerTreatmentSelectionBoxTwo;
    private ComboBox<Node> layerTreatmentSelectionBoxThree;
    private ComboBox<Node> layerTreatmentSelectionBoxFour;
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
        layerNameInputBox = new ComboBox();
        layerNameInputBox.setEditable(true);
        layerNameInputBox.setStyle("-fx-font-size:10");
        layerTreatmentSelectionBoxOne = new ComboBox();
        layerTreatmentSelectionBoxOne.setValue(new Label(""));
        layerTreatmentSelectionBoxOne.setStyle("-fx-font-size:10");
        layerTreatmentSelectionBoxTwo = new ComboBox();
        layerTreatmentSelectionBoxTwo.setValue(new Label(""));
        layerTreatmentSelectionBoxTwo.setStyle("-fx-font-size:10");
        layerTreatmentSelectionBoxThree = new ComboBox();
        layerTreatmentSelectionBoxThree.setValue(new Label(""));
        layerTreatmentSelectionBoxThree.setStyle("-fx-font-size:10");
        layerTreatmentSelectionBoxFour = new ComboBox();
        layerTreatmentSelectionBoxFour.setValue(new Label(""));
        layerTreatmentSelectionBoxFour.setStyle("-fx-font-size:10");
        treatmentsSelectionBox = new VBox(layerTreatmentSelectionBoxOne, layerTreatmentSelectionBoxTwo, layerTreatmentSelectionBoxThree, layerTreatmentSelectionBoxFour);
        treatmentsSelectionBox.setSpacing(3);
        createNewLayerNoteLabel = new Label("Note");
        createNewLayerNoteInput = new TextField();
        createNewLayerNoteInput.setStyle("-fx-font-size:10");
        createNewLayerMessageLabel = new Label();
        createNewLayerMessageLabel.setTextFill(Color.RED);
        createNewLayerMessageLabel.setMaxWidth(240);
        createNewLayerMessageLabel.setWrapText(true);
        createNewLayerButton = new Button("Create");
        cancelNewLayerButton = new Button("Cancel");
        createNewLayerButtonBox = new HBox(createNewLayerButton, cancelNewLayerButton);
        createNewLayerButtonBox.setSpacing(6);
        createNewLayerPane = new GridPane();
        createNewLayerPane.add(createNewLayerNameLabel, 0, 0);
        createNewLayerPane.add(layerNameInputBox, 0, 1);
        createNewLayerPane.add(treatmentsSelectionBox, 0, 2);
        createNewLayerPane.add(createNewLayerNoteLabel, 0, 3);
        createNewLayerPane.add(createNewLayerNoteInput, 0, 4);
        createNewLayerPane.add(createNewLayerButtonBox, 0, 5);
        createNewLayerPane.add(createNewLayerMessageLabel, 1, 5);
        createNewLayerPane.setVgap(6);
        createNewLayerPane.setHgap(6);
        
        // createNewTreatmentPane
        createNewTreatmentHeaderLabel = new Label("Create a new surface treatment");
        createNewTreatmentNameLabel = new Label("Treatment name");
        createNewTreatmentNameInput = new TextField();
        createNewTreatmentNameInput.setStyle("-fx-font-size:10");
        createNewTreatmentTypeLabel = new Label("Type");
        createNewTreatmentTypeInputBox = new ComboBox();
        createNewTreatmentTypeInputBox.setStyle("-fx-font-size:10");
        createNewTreatmentTypeInputBox.setEditable(true);
        createNewTreatmentManufacturerLabel = new Label("Manufacturer");
        createNewTreatmentManufacturerInputBox = new ComboBox();
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
            String newLayerName = layerNameInputBox.getValue().trim();
            String newLayerNote = createNewLayerNoteInput.getText().trim();
            Integer firstTreatmentId = Integer.parseInt(this.layerTreatmentSelectionBoxOne.getItems().get(0).getId());
            Integer secondTreatmentId = Integer.parseInt(this.layerTreatmentSelectionBoxTwo.getItems().get(0).getId());
            Integer thirdTeatmentId = Integer.parseInt(this.layerTreatmentSelectionBoxThree.getItems().get(0).getId());
            Integer fourthTreatmentId = Integer.parseInt(this.layerTreatmentSelectionBoxFour.getItems().get(0).getId());
            if (!(stringLengthCheck(newLayerName, 3, 40) && stringLengthCheck(newLayerNote, 0, 60))) {
                createNewLayerMessageLabel.setText("Layer name must be 3-40 and layer note must be 0-60 characters long");
            } else if (!(newLayerName.matches("[A-Za-z0-9\\s]*") && newLayerNote.matches("[A-Za-z0-9\\s]*"))) {
                createNewLayerMessageLabel.setText("Layer name and note must only contain numbers and letters");
            } else {
                Layer createdLayer = this.service.createLayer(newLayerName, newLayerNote);
                this.service.addLayerToSurface(this.currentlyViewedSurface.getSurfaceId(), createdLayer.getLayerId());
                if (firstTreatmentId != 0) {
                    this.service.addTreatmentToLayer(createdLayer.getLayerId(), firstTreatmentId);
                }
                if (secondTreatmentId != 0) {
                    this.service.addTreatmentToLayer(createdLayer.getLayerId(), secondTreatmentId);
                }
                if (thirdTeatmentId != 0) {
                    this.service.addTreatmentToLayer(createdLayer.getLayerId(), thirdTeatmentId);
                }
                if (fourthTreatmentId != 0) {
                    this.service.addTreatmentToLayer(createdLayer.getLayerId(), fourthTreatmentId);
                }
                this.layerNameInputBox.setValue("");
                this.layerTreatmentSelectionBoxOne.setValue(new Label(""));
                this.layerTreatmentSelectionBoxTwo.setValue(new Label(""));
                this.layerTreatmentSelectionBoxThree.setValue(new Label(""));
                this.layerTreatmentSelectionBoxFour.setValue(new Label(""));
                createNewLayerNoteInput.setText("");
                createNewLayerMessageLabel.setText("");
                this.refresh();
                surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
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
            this.layerNameInputBox.setValue("");
            this.layerTreatmentSelectionBoxOne.setValue(new Label(""));
            surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
        });
        cancelNewTreatmentButton.setOnAction(event -> {
            surfacePane.setBottom(goToNewLayerTreatmentButtonBox);
        });
    }

    public BorderPane getSurfacePane() {
        return surfacePane;
    }

    public void refresh() {
        this.currentlyViewedSurface = this.service.getCurrentlyViewedSurface();
        ArrayList<Layer> updatedLayers = this.service.fetchLayers(this.currentlyViewedSurface);
        currentlyViewedSurface.setLayers(updatedLayers);
        layersTree.setRoot(this.getLayerTreeItems(this.currentlyViewedSurface.getSurfaceName(), updatedLayers));
        layerNameInputBox.getItems().addAll(updatedLayers.stream().map(l -> l.getLayerName()).collect(Collectors.toList()));
        ArrayList<Node> updatedTreatments = new ArrayList<>();
        this.service.fetchAllSurfaceTreatments().forEach(st -> updatedTreatments.add(this.surfaceTreatmentAsNode(st)));
        layerTreatmentSelectionBoxOne.getItems().setAll(updatedTreatments);
        layerTreatmentSelectionBoxTwo.getItems().setAll(updatedTreatments);
        layerTreatmentSelectionBoxThree.getItems().setAll(updatedTreatments);
        layerTreatmentSelectionBoxFour.getItems().setAll(updatedTreatments);
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
        HBox layerColoursBox = new HBox();
        Label layerNameLabel = new Label(layer.getLayerName());
        for (SurfaceTreatment st : layer.getTreatments()) {
            layerColoursBox.getChildren().add(new Circle(6, st.getTreatmentColour()));
            treeItemToReturn.getChildren().add(new TreeItem(this.surfaceTreatmentAsNode(st)));
        }
        Label layerNoteLabel = new Label(layer.getLayerNote());
        layerNoteLabel.setMaxWidth(240);
        layerNoteLabel.setWrapText(true);
        HBox layerBox = new HBox(layerNameLabel, layerColoursBox, layerNoteLabel);
        layerBox.setSpacing(6);
        treeItemToReturn.setValue(layerBox);
        treeItemToReturn.setExpanded(true);
        return treeItemToReturn;
    }
    
    public Node surfaceTreatmentAsNode(SurfaceTreatment surfaceTreatment) {
        Label treatmentNameLabel = new Label(surfaceTreatment.getTreatmentName());
        treatmentNameLabel.setStyle("-fx-font-weight: bold;");
        Circle treatmentColourCircle = new Circle(6, surfaceTreatment.getTreatmentColour());
        Label treatmentColourLabel = new Label(surfaceTreatment.getTreatmentManufacturer());
        Label treatmentManufacturerLabel = new Label(surfaceTreatment.getTreatmentType());
        HBox treatmentBox = new HBox(treatmentNameLabel, treatmentColourCircle, treatmentColourLabel, treatmentManufacturerLabel);
        treatmentBox.setId(surfaceTreatment.getTreatmentId().toString());
        treatmentBox.setSpacing(4);
        return treatmentBox;
    }
}

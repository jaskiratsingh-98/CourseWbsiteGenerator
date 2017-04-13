/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author jaski
 */
public class TAUndergradValueFactory implements Callback<TableColumn.CellDataFeatures<TeachingAssistant, CheckBox>, ObservableValue<CheckBox>>{

    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<TeachingAssistant, CheckBox> param) {
        TeachingAssistant ta = param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(ta.isUndergrad());
        checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
            ta.setUndergrad(new_val);
        });
        return new SimpleObjectProperty<>(checkBox);
    }
    
}

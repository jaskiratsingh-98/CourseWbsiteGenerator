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
public class PageGenerateValueFactory  implements Callback<TableColumn.CellDataFeatures<Pages, CheckBox>, ObservableValue<CheckBox>>{

    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Pages, CheckBox> param) {
        Pages pa = param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(pa.isGenerate());
        checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
            pa.setGenerate(new_val);
        });
        return new SimpleObjectProperty<>(checkBox);
    }
    
}

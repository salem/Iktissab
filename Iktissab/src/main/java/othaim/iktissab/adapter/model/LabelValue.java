package othaim.iktissab.adapter.model;

/**
 * Created by S.Aman on 7/16/13.
 */
public class LabelValue {
    private String _label;
    private String _value;
    public LabelValue(String label, String value){
        _label = label;
        _value = value;
    }

    public String getLabel() {
        return _label;
    }

    public void setLabel(String _label) {
        this._label = _label;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String _value) {
        this._value = _value;
    }

}

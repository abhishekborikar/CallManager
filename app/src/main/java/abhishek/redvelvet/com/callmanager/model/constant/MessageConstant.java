package abhishek.redvelvet.com.callmanager.model.constant;

/**
 * Created by abhishek on 8/8/18.
 */

public class MessageConstant {

    public static final String DND_PREFRENCE = "dnd status prefrences";

    //---boolean
    public static final String KEY_REQUEST = "dnd activation request delivered status";

    //--- KEY_REQUEST ? KEY_DELIVERY_DATE : NULL
    public static final String KEY_DELIVERY_DATE = "dnd request data and time";

    //---Operator contain Operator name id
    public static final String KEY_OPERATOR = "dnd activate on operator";// contains the information of operator name and subscription id

    //----status if registered
    public static final String KEY_REGISTER = "dnd register status and time";

    //----status if processed
    public static final String KEY_PROCESSED = "dnd sucessfully processed and time";

    //----active status
    public static final String KEY_ACTIVE = "is activated";

    public static final String KEY_FULL_PAR = "full or partial";

}

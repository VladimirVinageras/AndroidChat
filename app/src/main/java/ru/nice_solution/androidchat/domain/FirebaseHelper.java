package ru.nice_solution.androidchat.domain;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.realtime.util.StringListReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Madriguera on 09/06/2016.
 */

/*Aquí vamos a guardar toda la lógica de Firebase*/
public class FirebaseHelper {
    private Firebase dataReference;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";
    private final static String FIREBASE_URL = "https://androidchat-nice.firebaseio.com";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /*El constructor recibe URL de referencia donde se almacena la data*/
    public FirebaseHelper() {
        this.dataReference = new Firebase(FIREBASE_URL);
    }

    public Firebase getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail() {
        AuthData authData = dataReference.getAuth();
        String email = null;
        if (authData != null) {
            Map<String, Object> providerData = authData.getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    public Firebase getUserReference(String email){
        Firebase userReference = null;
        if (email != null){
            String emailKey = email.replace(".","_");
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public Firebase getMyUserReference(){
        return getUserReference(getAuthUserEmail());
            }
    public Firebase getContactReference(String email){
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public Firebase getMyContactsReference(){
        return getContactReference(getAuthUserEmail());
            }

    public Firebase getOneContactReference(String mainEmail, String childEmail){
        String childkey = childEmail.replace(".","_");
                return getUserReference(mainEmail).child(CONTACTS_PATH).child(childkey);
    }

    public Firebase getChatReference(String receiver){
        String keySender = getAuthUserEmail().replace(".","_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender+ SEPARATOR + keyReceiver;
        if (keySender.compareTo(keyReceiver)>0){
            keyChat = keyReceiver + SEPARATOR + keySender;
                  }
        return dataReference.getRoot().child(CHATS_PATH ).child(keyChat);
    }

    public void changeUserConnectionStatus(boolean onLine){
        if(getMyUserReference() != null) {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online",onLine);
            getMyUserReference().updateChildren(updates);
            notifyContactsOfConnectionChange(onLine);
;        }
    }

    public void notifyContactsOfConnectionChange(boolean onLine) {
        notifyContactsOfConnectionChange(onLine,false);
    }

    public void SignOff(){
        notifyContactsOfConnectionChange(false,true);
    }

    private void notifyContactsOfConnectionChange(final boolean onLine, final boolean signoff) {
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String email = child.getKey();
                    Firebase reference = getOneContactReference(email, myEmail);
                    reference.setValue(onLine);
                }
                if(signoff){
                    dataReference.unauth();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}

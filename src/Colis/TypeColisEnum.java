package Colis;

/**
 * Created by pewtroof on 2014-11-17.
 */
public enum TypeColisEnum
{
    communication,      //String roomName, String message
    connection,         //String username, String password
    deconnection,       //vide
    fullUpdate,         //List<String> roomList, List<List<String>> textLists, List<List<String>> userLists
    listRequest,        //String roomName
    updateText,         //String textToUpdate, String roomName
    updateAddUser,      //String roomName, String username
    updateRemoveUser,   //String roomName, String username
    updateUserList,     //String username, String roomName, Boolean addOrRemove
    createRoom,         //String roomName
    getRoomList,        //List<String> roomList
    error,              //String message
    joinRoom            //String roomName
}

package Colis;

/**
 * Created by pewtroof on 2014-11-17.
 */
public enum TypeColisEnum
{
    communication,      //String roomName, String message
    connection,         //String username, String password
    disconnection,       //vide
    fullUpdate,         //List<String> roomList, List<List<String>> userLists, List<List<String>> textLists
    listRequest,        //String roomName
    updateText,         //String roomName, String textToUpdate
    updateAddUser,      //String roomName, String username
    updateRemoveUser,   //String roomName, String username
    updateUserList,     //String roomName, String userName, Boolean addOrRemove
    createRoom,         //String roomName
    getRoomList,        //List<String> roomList
    error,              //String message
    joinRoom            //String roomName
}

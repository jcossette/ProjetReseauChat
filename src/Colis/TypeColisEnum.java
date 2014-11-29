package Colis;

/**
 * Created by pewtroof on 2014-11-17.
 */
public enum TypeColisEnum
{
    communication,      //String roomName, String message
    connection,         //String username
    acceptedConnection, //vide
    refusedConnection,  //String message
    disconnection,      //vide
    fullUpdate,         //List<String> roomList, List<List<String>> userLists, List<List<String>> textLists
    listRequest,        //String roomName
    updateText,         //String roomName, String textToUpdate, String userName
    updateAddUser,      //String roomName, String username
    updateRemoveUserFromRoom,   //String roomName, String username
    updateRemoveUser,   //String username
    createRoom,         //String roomName
    joinRoom         ,   //String roomName
    getRoomList,        //List<String> roomList
    error                 //String message
}

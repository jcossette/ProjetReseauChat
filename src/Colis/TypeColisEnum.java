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
    getFullUpdate,      //vide
    fullUpdate,         //List<Room> roomList, List<User> userLists
    listRequest,        //String roomName
    updateListRequest,  //List<User> userList
    updateText,         //String roomName, String textToUpdate
    updateAddUser,      //String room, User user
    updateRemoveUserFromRoom,   //String room, User user
    updateRemoveUser,   //User user
    createRoom,         //String roomName
    joinRoom,           //Room room
    roomInfos,          //Room room
    getRoomList,        //vide
    roomList,           //List<Room> roomList
    error               //String message
}

package Colis;

import java.io.Serializable;

/**
 * Created by pewtroof on 2014-11-17.
 */
public enum TypeColisEnum implements Serializable
{
    //Envoye par le client
    communication,      //int roomID, String message
    connection,         //String username
    getFullUpdate,      //vide
    createRoom,         //String roomName
    joinRoomRequest,    //int roomID
    leaveRoom,          //int roomID
    getRoomList,        //vide


    //Envoye par le serveur
    acceptedConnection, //vide
    refusedConnection,  //String message
    fullUpdate,         //List<Room> roomList, List<User> userLists
    updateListRequest,  //List<User> userList
    updateText,         //String roomName, String textToUpdate
    updateAddUser,      //String room, User user
    updateRemoveUserFromRoom,   //int roomID, User user
    updateRemoveUser,   //User user
    joinRoom,           //Room room
    roomList           //List<Room> roomList
}

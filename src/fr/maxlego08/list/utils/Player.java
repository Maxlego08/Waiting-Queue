package fr.maxlego08.list.utils;

import fr.maxlego08.list.connection.Connection;

public class Player {

	private final Connection connection;
	private final String name;
	private int postion;
	
	public Player(Connection connection, String name, int postion) {
		super();
		this.connection = connection;
		this.name = name;
		this.postion = postion;
	}

	public int getPostion() {
		return postion;
	}
	
	public void updatePosition(){
		postion--;
	}
	
	public void setPostion(int postion) {
		this.postion = postion;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public String getName() {
		return name;
	}
	
	public void sendPosition(){
		getConnection().send(Action.SEND_POSITION, postion);
	}
	
	public void send(Action action, Object object){
		getConnection().send(action, object);
	}
	
}

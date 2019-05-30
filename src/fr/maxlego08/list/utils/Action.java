package fr.maxlego08.list.utils;

public enum Action {

	CONNECT(0),
	SEND_POSITION(1),
	SEND_MAXPLAYER(2),
	SEND_ONLINEPLAYER(3),
	SEND_MAXPLAYER_IN_QUEUE(4),
	CAN_CONNECT(5),
	STOP(6),
	
	;
	
	private final int id;

	private Action(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static Action get(int id){
		for(Action a : Action.values()) if (a.getId() == id) return a;
		return null;
	}
	
}

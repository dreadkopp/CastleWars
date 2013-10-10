package arne.bomber;
/**
 * Alles was in dem Spiel an Ereignissen passiert, wird an einen GameEventListener weitergereicht, der dann die GUI updaten muss.
 * */
public interface GameEventListener {
	/**
	 * Zwei Dinge sind miteinander kollidiert
	 * */
	public void collisionOccurred(/*GameObject a, GameObject b*/);
	
	/**
	 * Spieler hat gewonnnen 
	 * */
	public void playerWin(/*GameObject o*/);
}

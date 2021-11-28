package leonhar001.ms.model;

public interface ObserverField {
	/*When an event is trigged, inform function with:
	 * 1. field where the event has ocurred
	 * 2. event type*/
	public void eventOcurred(BField field, EventField event);
	
}

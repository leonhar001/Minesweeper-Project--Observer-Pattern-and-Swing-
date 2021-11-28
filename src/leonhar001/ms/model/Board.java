package leonhar001.ms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements ObserverField {

	private final int rows;
	private final int columns;
	private final int mines;

	private final List<BField> fields = new ArrayList<>();
	private final List<Consumer<Boolean>> observers = new ArrayList<>();

	public void addObserver(Consumer<Boolean> observer) {
		observers.add(observer);
	}

	public void notifyObservers(boolean result) {
		observers.stream().forEach(o -> o.accept(result));
	}

	public Board(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;

		generateFields();
		associateNeighbors();
		plantMines();
	}

	public void open(int row, int column) {
		fields.stream().filter(f -> f.getRow() == row && f.getColumn() == column).findFirst()
				.ifPresent(c -> c.openField());
	}

	public void flag(int row, int column) {
		fields.stream().filter(f -> f.getRow() == row && f.getColumn() == column).findFirst()
				.ifPresent(c -> c.alternateFlag());
	}

	private void generateFields() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				BField field = new BField(r, c);
				field.addObservers(this);
				fields.add(field);
			}
		}
	}

	private void associateNeighbors() {
		for (BField f1 : fields) {
			for (BField f2 : fields) {
				f1.addNeighbor(f2);
			}
		}
	}

	private void plantMines() {
		long toPlantMines = 0;

		Predicate<BField> mined = n -> n.isMined();

		do {
			int random = (int) (Math.random() * fields.size());
			fields.get(random).mine();
			toPlantMines = fields.stream().filter(mined).count();
		} while (toPlantMines < mines);
	}

	public boolean resolvedField() {
		return fields.stream().allMatch(f -> f.resolvedField());
	}

	public void restart() {
		fields.stream().forEach(f -> f.restart());
		plantMines();
	}

	public void forEachField(Consumer<BField> function) {
		fields.forEach(function);
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	@Override
	public void eventOcurred(BField field, EventField event) {
		if (event == EventField.EXPLOSION) {
			showMines();
			notifyObservers(false);
		} else if (resolvedField()) {
			notifyObservers(true);

		}
	}

	private void showMines() {
		fields.stream().
		filter(f -> f.isMined()).
		filter(f -> !f.isFlagged()).
		forEach(f -> f.setOpenned(true));
	}

}

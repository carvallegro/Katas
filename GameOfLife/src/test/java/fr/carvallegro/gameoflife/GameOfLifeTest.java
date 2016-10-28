package fr.carvallegro.gameoflife;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class GameOfLifeTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructor_with_null_parameters_should_throw_exception() {
		new GameOfLife(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_with_empty_parameters_should_throw_exception() throws Exception {
		new GameOfLife("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateGrid_should_throw_exception_when_null_parameter() throws Exception {
		new GameOfLife(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateGrid_should_throw_exception_when_empty_parameter() throws Exception {
		new GameOfLife("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateGrid_should_throw_exception_when_bad_coordinates() throws Exception {
		new GameOfLife("a b");
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateGrid_should_throw_exception_when_negatives_coordinates() throws Exception {
		new GameOfLife("-2	-3");
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateGrid_should_throw_exception_when_bad_grid_char() throws Exception {
		new GameOfLife("2 2\n..\n.o");
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateGrid_should_throw_exception_when_bad_horizontal_size() throws Exception {
		new GameOfLife("2 2\n...\n..");
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateGrid_should_throw_exception_when_bad_vertical_size() throws Exception {
		new GameOfLife("2 2\n..");
	}

	@Test
	public void generateGrid_should_return_dead_grid() throws Exception {
		String grid = "2 2\n..\n..";
		List<List<Boolean>> expected = Arrays.asList(Arrays.asList(false, false), Arrays.asList(false, false));

		GameOfLife gameOfLife = new GameOfLife(grid);
		Assertions.assertThat(gameOfLife.getGrid()).isEqualTo(expected);
	}

	@Test
	public void generateGrid_should_return_grid_with_one_life() throws Exception {
		String grid = "5 4\n....."
				+ "\n....."
				+ "\n..X.."
				+ "\n.....\n";
		List<List<Boolean>> expected = Arrays.asList(
				Arrays.asList(false, false, false, false, false),
				Arrays.asList(false, false, false, false, false),
				Arrays.asList(false, false, true, false, false),
				Arrays.asList(false, false, false, false, false));

		GameOfLife gameOfLife = new GameOfLife(grid);
		Assertions.assertThat(gameOfLife.getGrid()).isEqualTo(expected);
	}
	
	@Test
	public void cycle_should_return_one_more_life_when_3_lives_nearby() throws Exception {
		String grid = "8 4\n........"
				+ "\n....X..."
				+ "\n...XX..."
				+ "\n........";
		List<List<Boolean>> expected = Arrays.asList(
				Arrays.asList(false, false, false, false, false, false, false, false),
				Arrays.asList(false, false, false, true, true, false, false, false),
				Arrays.asList(false, false, false, true, true, false, false, false),
				Arrays.asList(false, false, false, false, false, false, false, false));
		
		GameOfLife gameOfLife = new GameOfLife(grid);
		gameOfLife.newGeneration();
		Assertions.assertThat(gameOfLife.getGrid()).isEqualTo(expected);

	}
	
	@Test
	public void cycle_should_return_no_life_when_1_life_on_the_grid() throws Exception {
		String grid = "8 4\n........"
				+ "\n....X..."
				+ "\n........"
				+ "\n........";
		List<List<Boolean>> expected = Arrays.asList(
				Arrays.asList(false, false, false, false, false, false, false, false),
				Arrays.asList(false, false, false, false, false, false, false, false),
				Arrays.asList(false, false, false, false, false, false, false, false),
				Arrays.asList(false, false, false, false, false, false, false, false));
		
		GameOfLife gameOfLife = new GameOfLife(grid);
		gameOfLife.newGeneration();
		Assertions.assertThat(gameOfLife.getGrid()).isEqualTo(expected);

	}
	
	@Test
	public void cycle_should_die_by_overpopulation() throws Exception {
		String grid = "8 4 \n....X..."
						+ "\n...X.X.."
						+ "\n..X.X..."
						+ "\n...X....";
		List<List<Boolean>> expected = Arrays.asList(
				Arrays.asList(false, false, false, false, true, false, false, false),
				Arrays.asList(false, false, false, true, false, true, false, false),
				Arrays.asList(false, false, true, false, true, false, false, false),
				Arrays.asList(false, false, false, true, false, false, false, false));
		
		GameOfLife gameOfLife = new GameOfLife(grid);
		gameOfLife.newGeneration();
		Assertions.assertThat(gameOfLife.getGrid()).isEqualTo(expected);
	}
}

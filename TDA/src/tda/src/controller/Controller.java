package tda.src.controller;

import tda.src.model.Model;
import tda.src.view.View;

public class Controller {

	final private Model model;
	final private View view;

	public Controller() {
		this.model = new Model();
		this.view = new View(this.model, this);

	}

	public void start() {
		// TODO implement this operation
		this.view.show();
	}

	public void exitMain() {
		this.view.exitAlert();
	}

}
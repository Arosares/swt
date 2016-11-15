package tda.src.controller;

import java.util.Set;

import tda.src.model.Model;
import tda.src.view.View;

import java.util.HashSet;

public class Controller {
	

	final private Model model;
	final private View view;

	

	public Controller() {
		this.model = new Model();
		this.view = new View(this.model, this);
		
	}



	public void showView() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}

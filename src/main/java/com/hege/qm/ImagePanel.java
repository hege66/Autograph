package com.hege.qm;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private Graphics g;
	private Image im;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImagePanel(Image im) {
		if (im != null) {
			this.im = im;
			g = im.getGraphics();
		}
	}


	@Override
	public void paint(Graphics g) {

		g.drawImage(im, 0, 0, 535, 205, null);
	}
}

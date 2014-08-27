package com.nastya.avltree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class TreePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Graphics2D g2; 
	
	public TreePanel(){
		
	}
	
	public void drawTree(Tree localTree) {
		if (localTree == null) {
			return;
		}
		Tree parent = localTree.getParent();
		
		if (parent == null) {
			// 1st Level
			localTree.setX(Param.WIDTH/2);
			localTree.setY(Param.TOP_MARGIN);

		} else if (parent.getParent() == null) {
			// 2nd Level
			if (localTree.getValue() < parent.getValue()){
				localTree.setX(Param.WIDTH/4);
				localTree.setY(parent.getY() + Param.NODE_MARGIN);		
			} else {
				localTree.setX(Param.WIDTH - Param.WIDTH/4);
				localTree.setY(parent.getY() + Param.NODE_MARGIN);	
			}
		} else {
			// other Levels
			if (localTree.getValue() < parent.getValue()){
				localTree.setX(parent.getX() - Math.abs(parent.getX() - parent.getParent().getX())/2);
				localTree.setY(parent.getY() + Param.NODE_MARGIN);
			} else {
				localTree.setX(parent.getX() + Math.abs(parent.getX() - parent.getParent().getX())/2);
				localTree.setY(parent.getY() + Param.NODE_MARGIN);
			}
			
		}
		if (parent != null) {
			g2.setColor(Param.COLOR_LINE);
			g2.drawLine(parent.getX()+Param.DIAMETR/2, parent.getY()+Param.DIAMETR-1, localTree.getX() + 17, localTree.getY() + 17);
		}
		g2.setColor(Param.COLOR_NODE);
		if (localTree.getValue() == TreeLogic.getAdded()) {
			g2.setColor(Param.COLOR_ADDED);
		} 
		if (localTree.getValue() == TreeLogic.getRemoved()) {
			System.out.println("COLOR_REMOVED");
			g2.setColor(Param.COLOR_REMOVED);
		}
		g2.fillOval(localTree.getX(), localTree.getY(), Param.DIAMETR, Param.DIAMETR);
		
		g2.setColor(Param.COLOR_VALUE);
		if (localTree.getValue() == TreeLogic.getAdded()) {
			g2.setColor(Color.WHITE);
		}
		g2.setFont(new Font("Tahoma", Font.BOLD, 14));
		g2.drawString(localTree.getValue() + "",localTree.getX() + 10, localTree.getY()+22);
		
//		g2.drawLine(parent.getX(), parent.getY(), localTree.getX(), localTree.getY());
		
		drawTree(localTree.getLeftChild());
		drawTree(localTree.getRightChild());
	}

	protected void paintComponent(Graphics g) {  
		super.paintComponent(g);  
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawTree(TreeLogic.getTree());
	}  
}

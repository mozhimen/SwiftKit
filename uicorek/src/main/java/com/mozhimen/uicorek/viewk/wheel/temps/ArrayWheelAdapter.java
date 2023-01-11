package com.mozhimen.uicorek.viewk.wheel.temps;

import com.mozhimen.uicorek.viewk.wheel.commons.IWheelAdapter;

import java.util.List;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements IWheelAdapter {
	

	// items
	private List<T> items;

	/**
	 * Constructor
	 * @param items the items
	 */
	public ArrayWheelAdapter(List<T> items) {
		this.items = items;

	}
	
	@Override
	public Object getItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.get(index);
		}
		return "";
	}

	@Override
	public int getItemsCount() {
		return items.size();
	}

	@Override
	public int indexOf(Object o){
		return items.indexOf(o);
	}

}

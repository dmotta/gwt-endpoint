package com.google.code.gwt.rest.client;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.core.client.GWT;

public class MapConverterGwtTest extends GwtTestBase {
	interface ItemConverter extends BeanConverter<Item> {
	}

	interface ShopConverter extends BeanConverter<Shop> {
	}

	Item item = new Item();
	{
		item.setName("namae");
		item.setOk(true);
	}

	@Test
	public void testItem() {
		ItemConverter converter = GWT.create(ItemConverter.class);
		Assert.assertEquals("namae", converter.toMap(item).get("name"));
	}

	@Test
	public void testShop() throws Exception {
		Shop shop = new Shop();
		shop.setName("sample");
		shop.setItem(item);
		shop.setName("n2");
		shop.getLists().add(item);
		shop.getLists().add(item);
		shop.getMap().put("sample", "good");

		ShopConverter converter = GWT.create(ShopConverter.class);
		System.out.println(converter.toMap(shop));
	}
}

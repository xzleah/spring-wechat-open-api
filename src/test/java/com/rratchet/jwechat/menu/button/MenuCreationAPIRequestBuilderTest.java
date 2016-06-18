package com.rratchet.jwechat.menu.button;

import static org.junit.Assert.assertThat;

import java.util.List;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;

import org.junit.Before;
import org.junit.Test;

import com.rratchet.jwechat.menu.MenuCreationAPIRequest;

public class MenuCreationAPIRequestBuilderTest {

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void test_clickButton() {
		String checkedButtonName = "clickButton";
		String checkedKey = "checkedKey";
		MenuCreationAPIRequest request = MenuCreationAPIRequestBuilder.clickButton(checkedButtonName).key(checkedKey)
				.build();
		assertThat(request.getButtonList().size(), is(1));
		Button button = request.getButtonList().get(0);
		assertThat(button.getName(), is(checkedButtonName));
		assertThat(button.getKey(), is(checkedKey));
		assertThat(button.getType(), is(ButtonTypeEnum.click.name()));
	}
	
	@Test
	public void test_viewButton() {
		String checedUrl = "checkedUrl";
		String checkedViewButtonName = "checkedViewButton";
		MenuCreationAPIRequest request = MenuCreationAPIRequestBuilder.viewButton(checkedViewButtonName).url(checedUrl).build();
		assertThat(request.getButtonList().size(), is(1));
		Button button = request.getButtonList().get(0);
		assertThat(button.getName(), is(checkedViewButtonName));
		assertThat(button.getUrl(), is(checedUrl));
		assertThat(button.getType(), is(ButtonTypeEnum.view.name()));
	}
	
	/**
	 * 建立子按钮
	 */
	@Test
	public void test_buttonWithSubButton() {
		String checkedButtonGroupName = "checkedButtonGroupName";
		String checkedClickButtonName = "clickButton";
		String checkedKey = "checkedKey";
		String checedUrl = "checkedUrl";
		String checkedViewButtonName = "checkedViewButton";
		MenuCreationAPIRequest request = MenuCreationAPIRequestBuilder.buttonGroup(MenuCreationAPIRequestBuilder.clickButton(checkedClickButtonName)
				.key(checkedKey).viewButton(checkedViewButtonName).url(checedUrl)).withName(checkedButtonGroupName).build();
		assertThat(request.getButtonList().size(), is(1));
		Button buttonGroup = request.getButtonList().get(0);
		assertThat(buttonGroup.getName(), is(checkedButtonGroupName));
		assertThat(buttonGroup.getType(), nullValue(String.class));
		
		List<Button> subButtonList = buttonGroup.getButtonList();
		assertThat(subButtonList.size(), is(2));
		Button button1 = subButtonList.get(0);
		assertThat(button1.getType(), is(ButtonTypeEnum.click.name()));
		Button button2 = subButtonList.get(1);
		assertThat(button2.getType(), is(ButtonTypeEnum.view.name()));
	}
	
	/**
	 * 连续建立两个按钮
	 */
	@Test
	public void test_clickButtonAndViewButton() {
		String checkedClickButtonName = "clickButton";
		String checkedKey = "checkedKey";
		String checedUrl = "checkedUrl";
		String checkedViewButtonName = "checkedViewButton";
		MenuCreationAPIRequest request = MenuCreationAPIRequestBuilder.clickButton(checkedClickButtonName)
				.key(checkedKey).viewButton(checkedViewButtonName).url(checedUrl).build();
		assertThat(request.getButtonList().size(), is(2));
		Button clickButton = request.getButtonList().get(0);
		assertThat(clickButton.getName(), is(checkedClickButtonName));
		assertThat(clickButton.getKey(), is(checkedKey));
		
		Button viewButton = request.getButtonList().get(1);
		assertThat(viewButton.getName(), is(checkedViewButtonName));
		assertThat(viewButton.getUrl(), is(checedUrl));
	}
	
}

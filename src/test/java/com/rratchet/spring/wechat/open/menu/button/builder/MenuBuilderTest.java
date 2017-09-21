package com.rratchet.spring.wechat.open.menu.button.builder;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;
import com.rratchet.spring.wechat.open.menu.builder.MenuBuilder;
import com.rratchet.spring.wechat.open.menu.button.Button;
import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;

public class MenuBuilderTest {
	
	private MenuBuilder menuBuilder;
	
	@Before
	public void setUp() {
		menuBuilder = new MenuBuilder();
	}
	
	@Test
	public void test_clickButtonOK() {
		String checkedButtonName = "clickButton";
		String checkedKey = "checkedKey";
		MenuCreationAPIRequest request = menuBuilder.clickButton(checkedButtonName).key(checkedKey).build();
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
		MenuCreationAPIRequest request = menuBuilder.viewButton(checkedViewButtonName).url(checedUrl).build();
		assertThat(request.getButtonList().size(), is(1));
		Button button = request.getButtonList().get(0);
		assertThat(button.getName(), is(checkedViewButtonName));
		assertThat(button.getUrl(), is(checedUrl));
		assertThat(button.getType(), is(ButtonTypeEnum.view.name()));
	}
	
	/**
	 * 建立多个按钮
	 */
	@Test
	public void test_multilButtons() {
		String checkedButtonName = "clickButton";
		String checkedKey = "checkedKey";
		
		String checedUrl = "checkedUrl";
		String checkedViewButtonName = "checkedViewButton";
		
		MenuCreationAPIRequest request = menuBuilder
				.clickButton(checkedButtonName).key(checkedKey)
				.and()
				.viewButton(checkedViewButtonName).url(checedUrl)
				.build();
		assertThat(request.getButtonList().size(), is(2));
		Button button = request.getButtonList().get(0);
		assertThat(button.getName(), is(checkedButtonName));
		assertThat(button.getKey(), is(checkedKey));
		assertThat(button.getType(), is(ButtonTypeEnum.click.name()));
		
		Button button2 = request.getButtonList().get(1);
		assertThat(button2.getName(), is(checkedViewButtonName));
		assertThat(button2.getUrl(), is(checedUrl));
		assertThat(button2.getType(), is(ButtonTypeEnum.view.name()));
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
		
		MenuCreationAPIRequest request = menuBuilder
				.buttonGroup(checkedButtonGroupName)
						.clickButton(checkedClickButtonName).key(checkedKey)
						.andMember()
						.viewButton(checkedViewButtonName).url(checedUrl)
					.and()
				.build();
		
		assertThat(request.getButtonList().size(), is(1));
		Button buttonGroup = request.getButtonList().get(0);
		assertThat(buttonGroup.getName(), is(checkedButtonGroupName));
		assertThat(buttonGroup.getType(), nullValue(String.class));
		
		List<Button> subButtonList = buttonGroup.getButtonList();
		assertThat(subButtonList.size(), is(2));
		Button button1 = subButtonList.get(0);
		assertThat(button1.getType(), is(ButtonTypeEnum.click.name()));
		assertThat(button1.getName(), is(checkedClickButtonName));
		Button button2 = subButtonList.get(1);
		assertThat(button2.getType(), is(ButtonTypeEnum.view.name()));
		assertThat(button2.getName(), is(checkedViewButtonName));
	}
	
	/**
	 * 建立多个按钮和子按钮
	 */
	@Test
	public void test_buttonWithSubButton2() {
		String checkedClickButtonName = "clickButton";
		String checkedKey = "checkedKey";
		
		String checkedButtonGroupName = "checkedButtonGroupName";
		String memberClickButtonName = "memberClickButtonName";
		String memberClickButtonKey = "memberClickButtonName";
		String memberViewButtonName = "memberViewButtonName";
		String memberViewButtonUrl = "memberViewButtonUrl";
		
		String checkedViewButtonName = "checkedViewButton";
		String checedUrl = "checkedUrl";
		
		MenuCreationAPIRequest request = menuBuilder
				.clickButton(checkedClickButtonName).key(checkedKey)
				.and()
				.buttonGroup(checkedButtonGroupName)
					.clickButton(memberClickButtonName).key(memberClickButtonKey)
					.andMember()
					.viewButton(memberViewButtonName).url(memberViewButtonUrl)
				.and()
				.viewButton(checkedViewButtonName).url(checedUrl)
				.build();
		
		assertThat(request.getButtonList().size(), is(3));
		
		Button button0 = request.getButtonList().get(0);
		assertThat(button0.getType(), is(ButtonTypeEnum.click.name()));
		assertThat(button0.getName(), is(checkedClickButtonName));
		assertThat(button0.getKey(), is(checkedKey));
		
		Button buttonGroup = request.getButtonList().get(1);
		assertThat(buttonGroup.getName(), is(checkedButtonGroupName));
		assertThat(buttonGroup.getType(), nullValue(String.class));
		
		List<Button> subButtonList = buttonGroup.getButtonList();
		assertThat(subButtonList.size(), is(2));
		Button button1 = subButtonList.get(0);
		assertThat(button1.getType(), is(ButtonTypeEnum.click.name()));
		assertThat(button1.getName(), is(memberClickButtonName));
		assertThat(button1.getKey(), is(memberClickButtonKey));
		
		Button button2 = subButtonList.get(1);
		assertThat(button2.getType(), is(ButtonTypeEnum.view.name()));
		assertThat(button2.getName(), is(memberViewButtonName));
		assertThat(button2.getUrl(), is(memberViewButtonUrl));
		
		Button button3 = request.getButtonList().get(2);
		assertThat(button3.getType(), is(ButtonTypeEnum.view.name()));
		assertThat(button3.getName(), is(checkedViewButtonName));
		assertThat(button3.getUrl(), is(checedUrl));
	}
	
	@Test
	public void test_scanCodeButton() {
		String checkedButtonName = "scanCodeButton";
		String checkedKey = "checkedKey";
		MenuCreationAPIRequest request = menuBuilder.scanCodePushButton(checkedButtonName).key(checkedKey).build();
		assertThat(request.getButtonList().size(), is(1));
		Button button = request.getButtonList().get(0);
		assertThat(button.getName(), is(checkedButtonName));
		assertThat(button.getKey(), is(checkedKey));
		assertThat(button.getType(), is(ButtonTypeEnum.scancode_push.name()));
	}
}

package com.rratchet.spring.wechat.open.menu.button;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.rratchet.spring.wechat.open.menu.MenuCreationAPIRequest;
import com.rratchet.spring.wechat.open.menu.button.Button;
import com.rratchet.spring.wechat.open.menu.button.ButtonTypeEnum;
import com.rratchet.spring.wechat.open.menu.button.ScanCodePushButtonBuilder;

public class ScanCodePushButtonBuilderTest {

	@Test
	public void test() {
		MenuCreationAPIRequest request = new MenuCreationAPIRequest();
		ScanCodePushButtonBuilder builder = new ScanCodePushButtonBuilder(request);
		String checkedKey = "checkedKey";
		String checkedName = "checkedName";
		builder.name(checkedName);
		request = builder.key(checkedKey).build();
		assertThat(builder.type(), is(ButtonTypeEnum.scancode_push));
		List<Button> buttonList = request.getButtonList();
		assertThat(buttonList.size(), is(1));
		Button button = buttonList.get(0);
		assertThat(ButtonTypeEnum.valueOf(button.getType()), is(ButtonTypeEnum.scancode_push));
		assertThat(button.getKey(), is(checkedKey));
		assertThat(button.getName(), is(checkedName));
		assertThat(button.getUrl(), nullValue());
		assertThat(button.getMediaId(), nullValue());
	}

}

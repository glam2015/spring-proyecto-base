package py.com.proyectobase.report;

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public class ReportStyle {

	private Style headerStyle;
	private Style titleStyle;
	private Style detailStyle;
	private Style sumStyle;
	private Style oddRowStyle;
	private Style groupFooterColumnStyle;

	public Style getHeaderStyle() {

		if (headerStyle == null) {
			headerStyle = new Style();
			headerStyle.setFont(Font.VERDANA_MEDIUM_BOLD);
			headerStyle.setBorderBottom(Border.PEN_2_POINT());
			headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
			headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
			headerStyle.setBackgroundColor(Color.DARK_GRAY);
			headerStyle.setTextColor(Color.WHITE);
			headerStyle.setTransparency(Transparency.OPAQUE);
			// headerStyle.setRotation(Rotation.RIGHT);
		}
		return headerStyle;
	}

	public Style getTitleStyle() {

		if (titleStyle == null) {
			titleStyle = new Style();
			titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true));

		}
		return titleStyle;
	}

	public Style getDetailStyle() {

		if (detailStyle == null) {
			detailStyle = new Style();

		}
		return detailStyle;
	}

	public Style getSumStyle() {

		if (sumStyle == null) {
			sumStyle = new Style();
			sumStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		}
		return sumStyle;
	}

	public Style getOddRowStyle() {

		if (oddRowStyle == null) {
			oddRowStyle = new Style();
			oddRowStyle.setBorder(Border.NO_BORDER());
			oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
			oddRowStyle.setTransparency(Transparency.OPAQUE);
		}
		return oddRowStyle;
	}

	public Style getGroupFooterColumnStyle() {

		if (groupFooterColumnStyle == null) {
			groupFooterColumnStyle = new Style();
			groupFooterColumnStyle.setFont(Font.VERDANA_MEDIUM_BOLD);
			groupFooterColumnStyle.setBorderBottom(Border.PEN_2_POINT());
			groupFooterColumnStyle.setHorizontalAlign(HorizontalAlign.CENTER);
			groupFooterColumnStyle.setVerticalAlign(VerticalAlign.MIDDLE);
			groupFooterColumnStyle.setBackgroundColor(Color.DARK_GRAY);
			groupFooterColumnStyle.setTextColor(Color.WHITE);
			groupFooterColumnStyle.setTransparency(Transparency.OPAQUE);
		}
		return groupFooterColumnStyle;
	}

}

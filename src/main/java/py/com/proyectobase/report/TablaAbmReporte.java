package py.com.proyectobase.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

public class TablaAbmReporte {
	private ReportStyle reportStyle;

	private JasperPrint jasperPrint;
	private JasperReport jasperReport;
	private DynamicReport dynamicReport;

	private final Map<String, Object> params = new HashMap<String, Object>();
	private List<AbstractColumn> columns;
	private String titulo;
	private String subTitulo;
	private String logoPath;
	private Collection<?> data;
	private String usuarioInfo;
	private String fechaCreacion;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubTitulo() {
		return subTitulo;
	}

	public void setSubTitulo(String subTitulo) {
		this.subTitulo = subTitulo;
	}

	public List<AbstractColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<AbstractColumn> columns) {
		this.columns = columns;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getUsuarioInfo() {
		return usuarioInfo;
	}

	public void setUsuarioInfo(String usuarioInfo) {
		this.usuarioInfo = usuarioInfo;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public ReportStyle getReportStyle() {

		if (reportStyle == null) {
			reportStyle = new ReportStyle();
		}
		return reportStyle;
	}

	public Collection<?> getData() {
		return data;
	}

	public void setData(Collection<?> data) {
		this.data = data;
	}

	public void generar() throws Exception {

		dynamicReport = buildReport();

		/**
		 * Get a JRDataSource implementation
		 */
		JRDataSource ds = new JRListDataSource(getData());
		;

		/**
		 * Creates the JasperReport object, we pass as a Parameter * the
		 * DynamicReport, a new ClassicLayoutManager instance (this one does the
		 * magic) and the JRDataSource
		 */
		jasperReport = DynamicJasperHelper.generateJasperReport(dynamicReport,
				getLayoutManager(), params);

		/**
		 * Creates the JasperPrint object, we pass as a Parameter the
		 * JasperReport object, and the JRDataSource
		 */

		jasperReport
				.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

		jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);

	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public JasperReport getJasperReport() {
		return jasperReport;
	}

	protected DynamicReport buildReport() throws Exception {

		DynamicReportBuilder drb = new DynamicReportBuilder();

		Integer margin = new Integer(20);
		drb.setTitleStyle(getReportStyle().getTitleStyle())
				.setTitle(getTitulo())
				.setSubtitle(getSubTitulo())
				.setDetailHeight(15)
				.setLeftMargin(margin)
				.setRightMargin(margin)
				.setTopMargin(margin)
				.setBottomMargin(margin)
				.setPrintBackgroundOnOddRows(true)
				.setOddRowBackgroundStyle(getReportStyle().getOddRowStyle())
				.addFirstPageImageBanner(getLogoPath(), 100, 100,
						ImageBanner.ALIGN_LEFT)
				.addImageBanner(getLogoPath(), 100, 100, ImageBanner.ALIGN_LEFT);

		drb.addAutoText(getUsuarioInfo(), AutoText.POSITION_FOOTER,
				AutoText.ALIGNMENT_LEFT, 300);
		drb.addAutoText(getFechaCreacion(), AutoText.POSITION_FOOTER,
				AutoText.ALIGNMENT_CENTER, 300);
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y,
				AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT);

		/*
		 * GroupBuilder gb1 = new GroupBuilder();
		 * 
		 * /* define the criteria column to group by (columnState) idem for the
		 * columnaQuantity column tells the group how to be shown, there are
		 * many // posibilities, // see the // GroupLayout for more. / DJGroup
		 * g1 = gb1 .setCriteriaColumn((PropertyColumn) getColumns().get(0))
		 * 
		 * .addFooterVariable(getColumns().get(5), DJCalculation.SUM,
		 * getReportStyle().getGroupFooterColumnStyle())
		 * .addFooterVariable(getColumns().get(6), DJCalculation.SUM,
		 * getReportStyle().getGroupFooterColumnStyle())
		 * .setStartInNewPage(true)
		 * .setGroupLayout(GroupLayout.VALUE_IN_HEADER).build();
		 * 
		 * /* tell the group place a variable in the footer of the column
		 * "columnAmount" with the SUM of all values of the columnAmount in this
		 * group. / GroupBuilder gb2 = new GroupBuilder(); // Create another
		 * group // (using // another column as criteria) /* and we add the same
		 * operations for the columnAmount and / DJGroup g2 = gb2
		 * .setCriteriaColumn((PropertyColumn) getColumns().get(1))
		 * .addFooterVariable(getColumns().get(5), DJCalculation.SUM) // //
		 * columnaQuantity // columns .addFooterVariable(getColumns().get(6),
		 * DJCalculation.SUM) .build();
		 */

		for (AbstractColumn column : getColumns()) {
			drb.addColumn(column);
		}
		drb.setHeaderHeight(25);

		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();
		return dr;
	}

	protected LayoutManager getLayoutManager() {

		return new ClassicLayoutManager();
	}

}

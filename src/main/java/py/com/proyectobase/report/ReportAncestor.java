package py.com.proyectobase.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.util.SortUtils;

public class ReportAncestor {

	protected static final Log log = LogFactory.getLog(BaseDjReportTest.class);
	protected ReportStyle reportStyle;

	protected JasperPrint jp;
	protected JasperReport jr;
	protected DynamicReport dynamicReport;
	protected Map<String, Object> params = new HashMap<String, Object>();
	private List<AbstractColumn> columns;

	protected ReportStyle getReportStyle() {

		if (reportStyle == null) {
			reportStyle = new ReportStyle();
		}
		return reportStyle;
	}

	public List<AbstractColumn> getColumns() {

		if (columns == null) {

			columns = new ArrayList<AbstractColumn>();
			Style headerStyle = getReportStyle().getHeaderStyle();
			Style titleStyle = getReportStyle().getTitleStyle();
			Style detailStyle = getReportStyle().getDetailStyle();
			Style sumDetail = getReportStyle().getSumStyle();

			AbstractColumn columnState = ColumnBuilder.getNew()
					.setColumnProperty("state", String.class.getName())
					.setTitle("State").setWidth(85).setStyle(titleStyle)
					.setHeaderStyle(headerStyle).build();

			AbstractColumn columnBranch = ColumnBuilder.getNew()
					.setColumnProperty("branch", String.class.getName())
					.setTitle("Branch").setWidth(85).setStyle(detailStyle)
					.setHeaderStyle(headerStyle).build();

			AbstractColumn columnaProductLine = ColumnBuilder.getNew()
					.setColumnProperty("productLine", String.class.getName())
					.setTitle("Product Line").setWidth(85)
					.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

			AbstractColumn columnaItem = ColumnBuilder.getNew()
					.setColumnProperty("item", String.class.getName())
					.setTitle("Item").setWidth(85).setStyle(detailStyle)
					.setHeaderStyle(headerStyle).build();

			AbstractColumn columnCode = ColumnBuilder.getNew()
					.setColumnProperty("id", Long.class.getName())
					.setTitle("ID").setWidth(40).setStyle(sumDetail)
					.setHeaderStyle(headerStyle).build();
			AbstractColumn columnaQuantity = ColumnBuilder.getNew()
					.setColumnProperty("quantity", Long.class.getName())
					.setTitle("Quantity").setWidth(80).setStyle(sumDetail)
					.setHeaderStyle(headerStyle).build();

			AbstractColumn columnAmount = ColumnBuilder.getNew()
					.setColumnProperty("amount", Float.class.getName())
					.setTitle("Amount").setWidth(90).setPattern("$ ###,###.##")
					.setStyle(sumDetail).setHeaderStyle(headerStyle).build();

			columns.add(columnState);
			columns.add(columnBranch);
			columns.add(columnaProductLine);
			columns.add(columnaItem);
			columns.add(columnCode);
			columns.add(columnaQuantity);
			columns.add(columnAmount);

		}
		return columns;
	}

	public void testReport() throws Exception {

		dynamicReport = buildReport();

		/**
		 * Get a JRDataSource implementation
		 */
		JRDataSource ds = getDataSource();

		/**
		 * Creates the JasperReport object, we pass as a Parameter * the
		 * DynamicReport, a new ClassicLayoutManager instance (this one does the
		 * magic) and the JRDataSource
		 */
		jr = DynamicJasperHelper.generateJasperReport(dynamicReport,
				getLayoutManager(), params);

		/**
		 * Creates the JasperPrint object, we pass as a Parameter the
		 * JasperReport object, and the JRDataSource
		 */
		log.debug("Filling the report");
		jr.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
		if (ds != null)
			jp = JasperFillManager.fillReport(jr, params, ds);
		else
			jp = JasperFillManager.fillReport(jr, params);

		log.debug("Filling done!");
		log.debug("Exporting the report (pdf, xls, etc)");
		exportReport();

		log.debug("test finished");

	}

	protected void exportReport() throws Exception {

		ReportExporter.exportReport(jp, System.getProperty("user.dir")
				+ "/target/reports/" + this.getClass().getName() + ".pdf");
		// exportToJRXML();
	}

	public DynamicReport buildReport() throws Exception {

		DynamicReportBuilder drb = new DynamicReportBuilder();

		Integer margin = new Integer(20);
		drb.setTitleStyle(getReportStyle().getTitleStyle())
				.setTitle("AGENDA ENFERMERIA DETALLES")
				.setSubtitle(
						"The items in this report correspond "
								+ "to the main products: DVDs, Books, Foods and Magazines")
				.setDetailHeight(30)
				.setLeftMargin(margin)
				.setRightMargin(margin)
				.setTopMargin(margin)
				.setBottomMargin(margin)
				.setPrintBackgroundOnOddRows(true)
				.setOddRowBackgroundStyle(getReportStyle().getOddRowStyle())
				.addFirstPageImageBanner("img/logo.jpg", 100, 100,
						ImageBanner.ALIGN_LEFT)
				.addImageBanner("img/logo.jpg", 100, 100,
						ImageBanner.ALIGN_LEFT);

		GroupBuilder gb1 = new GroupBuilder();

		/*
		 * define the criteria column to group by (columnState) idem for the
		 * columnaQuantity column tells the group how to be shown, there are
		 * many // posibilities, // see the // GroupLayout for more.
		 */
		DJGroup g1 = gb1
				.setCriteriaColumn((PropertyColumn) getColumns().get(0))

				.addFooterVariable(getColumns().get(5), DJCalculation.SUM,
						getReportStyle().getGroupFooterColumnStyle())
				.addFooterVariable(getColumns().get(6), DJCalculation.SUM,
						getReportStyle().getGroupFooterColumnStyle())
				.setStartInNewPage(true)
				.setGroupLayout(GroupLayout.VALUE_IN_HEADER).build();

		/*
		 * tell the group place a variable in the footer of the column
		 * "columnAmount" with the SUM of all values of the columnAmount in this
		 * group.
		 */
		GroupBuilder gb2 = new GroupBuilder(); // Create another group
		// (using
		// another column as criteria)
		/*
		 * and we add the same operations for the columnAmount and
		 */
		DJGroup g2 = gb2
				.setCriteriaColumn((PropertyColumn) getColumns().get(1))
				.addFooterVariable(getColumns().get(5), DJCalculation.SUM)
				//
				// columnaQuantity
				// columns
				.addFooterVariable(getColumns().get(6), DJCalculation.SUM)
				.build();

		for (AbstractColumn column : getColumns()) {
			drb.addColumn(column);
		}
		drb.setHeaderHeight(100);
		drb.addGroup(g1); // add group g1
		drb.addGroup(g2); // add group g2

		drb.setUseFullPageWidth(true);

		DynamicReport dr = drb.build();
		return dr;
	}

	protected LayoutManager getLayoutManager() {

		return new ClassicLayoutManager();
	}

	/**
	 * @return JRDataSource
	 */
	protected JRDataSource getDataSource() {

		Collection dummyCollection = TestRepositoryProducts
				.getDummyCollection();
		dummyCollection = SortUtils.sortCollection(dummyCollection,
				getColumns());

		JRDataSource ds = new JRListDataSource(dummyCollection);
		/*
		 * Create a JRDataSource , the Collection used here contains dummy
		 * hardcoded objects ...
		 */
		return ds;
	}

	public Collection getDummyCollectionSorted(List columnlist) {

		Collection dummyCollection = TestRepositoryProducts
				.getDummyCollection();
		return SortUtils.sortCollection(dummyCollection, columnlist);

	}

	public static void main(String[] args) throws Exception {

		ReportAncestor test = new ReportAncestor();
		test.testReport();
		test.exportReport();
		// JasperViewer.viewReport(test.jp);
	}

}

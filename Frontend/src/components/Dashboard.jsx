import { useSelector } from "react-redux";

import KPICards from "./KPICards.jsx";
import FraudPieChart from "./FraudPieChart.jsx";
import FraudTransactionDetails from "./FraudTransactionDetails";
import LocationFraudBarChart from "./LocationFraudBarChart";
import DashboardLoader from "./DashboardLoader.jsx";
import TableExplanation from "./TableExplanation";

import { aggregateFraudCountByLocation } from "../utils/locationAggregation";
import { generatePieExplanation } from "../utils/pieExplanation";
import { generateDashboardConclusion } from "../utils/dashboardConclusion";

import css from "../styles/Dashboard.module.css";

const Dashboard = () => {
  const response = useSelector((state) => state.response);

  if (response.isLoading) return <DashboardLoader />;

  const pieExplanation = generatePieExplanation(
    response.fraudTransationDistribution
  );

  const locationData = aggregateFraudCountByLocation(
    response.flagedTransactionDetails
  );

  const conclusionText = generateDashboardConclusion({
    summary: {
      total_transactions: response.totalTransactions,
      flagged_transactions: response.activeAnomalies,
      fraud_percentage: (
        response.activeAnomalies / response.totalTransactions
      ).toFixed(2),
    },
    fraudTypeDistribution: response.fraudTransationDistribution,
    metrics: {
      top_risk_region: response.topRiskRegion,
      average_transaction_amount: response.avgTransactionAmount,
    },
    locationData,
  });

  return (
    <div className={`${css.dashboard} bg-[#020617]`}>
      {/* -------- TABLE FIRST -------- */}
      <div className={css.tableSection}>
        <h3 className={css.sectionTitle}>Transaction Risk Analysis</h3>
        <TableExplanation />
        <FraudTransactionDetails
          flagedTransactionDetails={response.flagedTransactionDetails}
        />
      </div>

      {/* -------- CHARTS SECTION -------- */}
      <div className={css.chartsSection}>
        <div className={css.pieCard}>
          <h3 className={css.sectionTitle}>Anomaly Distribution</h3>
          <FraudPieChart
            fraudTransationDistribution={response.fraudTransationDistribution}
          />
          <p className={css.pieExplanation}>{pieExplanation}</p>
        </div>

        <div className={css.barCard}>
          <h3 className={css.sectionTitle}>Location-wise Anomaly Count</h3>
          <LocationFraudBarChart data={locationData} />
        </div>
      </div>

      {/* -------- KPI LAST -------- */}
      <div className={css.kpiSection}>
        <KPICards
          totalTransactions={response.totalTransactions}
          activeAnomalies={response.activeAnomalies}
          avgTransactionAmount={response.avgTransactionAmount}
          topRiskRegion={response.topRiskRegion}
          anomalyPercentageAvg={response.anomalyPercentageAvg}
        />
      </div>

      <div className={css.conclusionCard}>
        <h3 className={css.sectionTitle}>Overall Analysis</h3>
        <p className={css.conclusionText}>{conclusionText}</p>
      </div>
    </div>
  );
};

export default Dashboard;

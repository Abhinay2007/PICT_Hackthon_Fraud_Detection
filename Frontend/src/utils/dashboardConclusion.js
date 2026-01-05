export function generateDashboardConclusion({
  summary,
  fraudTypeDistribution,
  metrics,
  locationData
}) {
  const {
    total_transactions,
    flagged_transactions,
    fraud_percentage
  } = summary;

  /* ---------- Average Amount Insight ---------- */
  const avgAmountInsight = metrics.average_transaction_amount
    ? `The average transaction value across all processed records was ₹${metrics.average_transaction_amount.toLocaleString()}.`
    : "";

  /* ---------- Top 3 Risk Cities ---------- */
  let topCitiesInsight = "No significant regional clustering was observed.";
  if (locationData && locationData.length > 0) {
    const topCities = locationData
      .slice(0, 3)
      .map(item => item.location)
      .join(", ");

    topCitiesInsight = `The highest concentration of flagged transactions originated from ${topCities}.`;
  }

  return `
Out of ${total_transactions} processed transactions, ${flagged_transactions} were flagged for anomalous patterns, representing approximately ${fraud_percentage}% of the total activity.

${avgAmountInsight}
${topCitiesInsight}
`.trim();
}
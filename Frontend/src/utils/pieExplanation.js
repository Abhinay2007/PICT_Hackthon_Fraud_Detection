export function generatePieExplanation(distribution) {
  const entries = Object.entries(distribution);

  if (entries.length === 0) {
    return "No anomaly patterns were detected in the uploaded data.";
  }

  const sorted = entries.sort((a, b) => b[1] - a[1]);

  const [topReason, topPct] = sorted[0];

  if (sorted.length === 1) {
    return `All detected anomalies were associated with ${topReason.toLowerCase()}.`;
  }

  const [secondReason, secondPct] = sorted[1];

  return `Most anomalies were associated with ${topReason.toLowerCase()} (${topPct}%), followed by ${secondReason.toLowerCase()} (${secondPct}%).`;
}

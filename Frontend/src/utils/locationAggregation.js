export function aggregateFraudCountByLocation(results) {
  const map = {};

  results.forEach(row => {
    if (row.isFraud) {
      const location = row.location || "Unknown";
      map[location] = (map[location] || 0) + 1;
    }
  });

  return Object.entries(map)
    .map(([location, count]) => ({ location, count }))
    .sort((a, b) => b.count - a.count); 
}

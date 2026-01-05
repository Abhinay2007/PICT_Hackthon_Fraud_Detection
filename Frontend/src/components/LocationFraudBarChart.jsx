import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    Cell
} from "recharts";

import LocationFraudTooltip from "./LocationFraudTooltip";
import css from "../styles/LocationFraudBarChart.module.css"

export default function LocationFraudBarChart({ data }) {

    const BAR_COLORS = [
        "#ef4444", // red
        "#f59e0b", // amber
        "#3b82f6", // blue
        "#10b981", // green
        "#a855f7", // purple
        "#ec4899"  // pink
    ];

    console.log("BAR DATA 👉", data);

    return (
        <ResponsiveContainer width="100%" height={300} className={`${css["my-bar-chart"]}`}>
            <BarChart data={data} >
                <XAxis
                    dataKey="location"
                    interval={0}
                    height={100}
                    stroke="#94a3b8"
                    tick={{
                        angle: -90,
                        textAnchor: "end",
                        dy: 10,
                        fill: "#94a3b8",
                        fontSize: 12
                    }}
                />


                <YAxis
                    stroke="#94a3b8"
                    allowDecimals={false}
                />

                {/* 🔥 THIS TOOLTIP MUST WORK */}
                <Tooltip
                    cursor={{ fill: "rgba(148,163,184,0.08)" }}
                    content={<LocationFraudTooltip />}
                />

                <Bar
                    dataKey="count"
                    radius={[6, 6, 0, 0]}
                    isAnimationActive
                    animationDuration={1200}
                >
                    {data.map((_, index) => (
                        <Cell
                            key={index}
                            fill={BAR_COLORS[index % BAR_COLORS.length]}
                        />
                    ))}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );
}

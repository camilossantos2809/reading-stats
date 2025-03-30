"use client";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { useMemo } from "react";
import { ReadingProgress } from "@/types";

export function ReadingProgressChart({
  progress,
}: Omit<ReadingProgress, "book">) {
  const averagePagesPerDay = useMemo(() => {
    if (progress == null || progress.length === 0) return 0;

    const totalPages = progress.reduce(
      (sum, entry) => sum + entry.pagesRead,
      0
    );
    return Math.round(totalPages / progress.length);
  }, [progress]);

  return (
    <div className="w-full bg-gray-900 rounded-lg p-6">
      <div className="flex items-center gap-2 mb-6">
        <span className="text-xl font-bold text-gray-300">
          Average pages per day:
        </span>
        <span className="text-2xl font-bold text-blue-400">
          {averagePagesPerDay}
        </span>
      </div>
      <div className="h-[300px]">
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={progress.toReversed()}>
            <XAxis dataKey="dateRead" stroke="#9CA3AF" fontSize={12} />
            <YAxis stroke="#9CA3AF" fontSize={12} />
            <Tooltip
              contentStyle={{
                backgroundColor: "#1F2937",
                border: "1px solid #374151",
                borderRadius: "0.5rem",
              }}
            />
            <Line
              type="monotone"
              dataKey="pagesRead"
              stroke="#60A5FA"
              strokeWidth={2}
            />
          </LineChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}

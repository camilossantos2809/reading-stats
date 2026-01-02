interface ProgressProps {
  value: number;
  max?: number;
}

export default async function Progress({ value, max = 100 }: ProgressProps) {
  const clampedValue = Math.min(Math.max(value, 0), max);
  const percentage = (clampedValue / max) * 100;

  return (
    <div className="w-28 h-2.5 bg-gray-700 dark:bg-gray-600 rounded-full overflow-hidden">
      <div
        className="h-full bg-gradient-to-r from-blue-500 to-blue-700 dark:from-blue-400 dark:to-blue-600 transition-all duration-300 ease-in-out"
        style={{ width: `${percentage}%` }}
        aria-label={`Progress: ${Math.round(percentage)}%`}
      />
    </div>
  );
}

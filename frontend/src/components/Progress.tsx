interface ProgressProps {
  value: number;
}

export function Progress({ value }: ProgressProps) {
  return (
    <div className="h-2.5 bg-gray-700 dark:bg-gray-600 rounded-full overflow-hidden">
      <div
        className="h-full bg-gradient-to-r from-blue-500 to-blue-700 bg-blue-600 dark:bg-blue-400 transition-all duration-300"
        style={{ width: `${value}%` }}
      />
    </div>
  );
}

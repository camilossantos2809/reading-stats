interface ProgressProps {
  value: number;
}

export function Progress({ value }: ProgressProps) {
  return (
    <div className="h-2 bg-gray-200 dark:bg-gray-700 rounded-full overflow-hidden">
      <div
        className="h-full bg-slate-600 dark:bg-slate-400 transition-all duration-300"
        style={{ width: `${value}%` }}
      />
    </div>
  );
}

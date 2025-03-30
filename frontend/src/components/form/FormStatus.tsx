type FormStatusProps = {
  message: string | null;
  status: "success" | "error" | "none";
};

export function FormStatus({ message, status }: FormStatusProps) {
  if (message == null || message === "") return null;

  const statusConfig = {
    success: {
      border: "border-green-500/20",
      bg: "bg-green-500/10",
      text: "text-green-500 dark:text-green-400",
      icon: (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
          />
        </svg>
      ),
    },
    error: {
      border: "border-red-500/20",
      bg: "bg-red-500/10",
      text: "text-red-500 dark:text-red-400",
      icon: (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
        >
          <circle cx="12" cy="12" r="10" strokeWidth="2" />
          <path d="M12 8v4m0 4h.01" strokeWidth="2" strokeLinecap="round" />
        </svg>
      ),
    },
    none: {
      border: "border-gray-500/20",
      bg: "bg-gray-500/10",
      text: "text-gray-500 dark:text-gray-400",
      icon: (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-5 w-5"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
        >
          <circle cx="12" cy="12" r="10" strokeWidth="2" />
          <path d="M12 8v4m0 4h.01" strokeWidth="2" strokeLinecap="round" />
        </svg>
      ),
    },
  };

  const config = statusConfig[status];

  return (
    <div className={`p-4 mb-6 ${config.border} ${config.bg} rounded-lg`}>
      <p
        className={`${config.text} font-medium flex items-center gap-2`}
        role="alert"
        aria-live="polite"
      >
        {config.icon}
        {message}
      </p>
    </div>
  );
}

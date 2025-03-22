import Link from "next/link";

interface BackButtonProps {
  label: string;
  path: string;
}

export function BackButton({ label, path }: BackButtonProps) {
  return (
    <div className="flex items-center gap-2 text-gray-400 mb-4">
      <Link
        href={path}
        className="flex items-center gap-2 hover:text-gray-600 dark:hover:text-gray-200 transition-colors"
      >
        <svg
          className="w-5 h-5"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M10 19l-7-7m0 0l7-7m-7 7h18"
          />
        </svg>
        {label}
      </Link>
    </div>
  );
}

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
}

export function Input({ label, id, ...props }: InputProps) {
  return (
    <div className="space-y-2">
      <label htmlFor={id} className="block font-medium">
        {label}
      </label>
      <input
        id={id}
        {...props}
        className="w-full p-2 rounded-lg border bg-transparent transition-all duration-300 focus:outline-none focus:border-slate-400 dark:focus:border-slate-600"
      />
    </div>
  );
}

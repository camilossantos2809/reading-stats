import Link from "next/link";

import { Progress } from "@/components/progress";
import { BackButton } from "@/components/back-buttom";
import { api } from "@/services/api";

interface Goal {
  id: number;
  name: string;
  pagesTotal: number;
  pagesRead: number;
  booksCount: number | null;
  balance: number | null;
  speedIdeal: number | null;
  speedAverage: number | null;
  daysAboveGoal: number | null;
  daysBelowGoal: number | null;
}

export default async function Goals() {
  const goals = await api.getGoals();

  return (
    <div className="flex flex-col min-h-screen p-8 sm:p-12">
      <div className="mb-8 flex justify-between items-center">
        <div>
          <BackButton label="Back to Home" path="/" />
          <h1 className="text-4xl font-bold mb-4">Reading Goals</h1>
          <p className="text-gray-400">
            Track your reading progress and achievements
          </p>
        </div>
        <Link
          href="/goals/new"
          className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
        >
          New Goal
        </Link>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {goals?.map((goal: Goal) => (
          <Link
            key={goal.id}
            href={`/goals/${goal.id}`}
            className="group block"
          >
            <div className="p-6 border rounded-lg space-y-6 transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600 hover:scale-[1.02]">
              <div className="flex justify-between items-start">
                <div>
                  <h2 className="text-2xl font-bold mb-2 group-hover:text-slate-600 dark:group-hover:text-slate-300 transition-colors">
                    {goal.name}
                  </h2>
                  <p className="text-gray-400">
                    {goal.booksCount != null && `${goal.booksCount} books • `}
                    {goal.pagesTotal} pages total
                  </p>
                </div>
                <div className="text-right">
                  <p className="text-2xl font-bold">
                    {Math.round((goal.pagesRead / goal.pagesTotal) * 100)}%
                  </p>
                  <p className="text-gray-400">
                    {goal.pagesRead} of {goal.pagesTotal}
                  </p>
                </div>
              </div>
              <Progress value={(goal.pagesRead / goal.pagesTotal) * 100} />
              <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
                <div className="p-4 border rounded-lg">
                  <p className="text-sm text-gray-400 mb-1">Balance</p>
                  <p
                    className={`text-lg font-bold ${
                      goal.balance && goal.balance > 0
                        ? "text-green-500"
                        : goal.balance && goal.balance < 0
                        ? "text-red-500"
                        : ""
                    }`}
                  >
                    {goal.balance ?? 0} pages
                  </p>
                </div>
                <div className="p-4 border rounded-lg">
                  <p className="text-sm text-gray-400 mb-1">Speed</p>
                  <p className="text-lg font-bold">
                    {goal.speedAverage ?? 0}
                    <span className="text-gray-400 text-sm ml-1">
                      / {goal.speedIdeal ?? 0}
                    </span>
                  </p>
                </div>
                <div className="p-4 border rounded-lg">
                  <p className="text-sm text-gray-400 mb-1">Above Goal</p>
                  <p className="text-lg font-bold text-green-500">
                    {goal.daysAboveGoal ?? 0} days
                  </p>
                </div>
                <div className="p-4 border rounded-lg">
                  <p className="text-sm text-gray-400 mb-1">Below Goal</p>
                  <p className="text-lg font-bold text-red-500">
                    {goal.daysBelowGoal ?? 0} days
                  </p>
                </div>
              </div>
            </div>
          </Link>
        ))}
        {(!goals || goals.length === 0) && (
          <div className="col-span-full p-8 border rounded-lg text-center">
            <p className="text-gray-400 mb-4">No reading goals set yet</p>
            <Link
              href="/goals/new"
              className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
            >
              Create your first goal
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}

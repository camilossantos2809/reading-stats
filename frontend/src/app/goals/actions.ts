"use server";

import { redirect } from "next/navigation";

interface GoalFormData {
  name: string;
}

export async function addGoal(
  _: unknown,
  formData: FormData
): Promise<{ message: string }> {
  const goal: GoalFormData = {
    name: formData.get("name") as string,
  };

  if (!goal.name?.trim()) {
    return { message: "Goal name is required" };
  }

  // TODO: Replace with actual API call
  console.log("Goal to be created:", goal);
  redirect("/goals");
}

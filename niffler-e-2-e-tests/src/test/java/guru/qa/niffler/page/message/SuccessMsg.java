package guru.qa.niffler.page.message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessMsg implements Msg {
  PROFILE_MSG("Profile successfully updated"),
  CATEGORY_MSG("New category added"),
  SPENDING_MSG("Spending successfully added");

  private final String msg;

  @Override
  public String getMessage() {
    return msg;
  }
}

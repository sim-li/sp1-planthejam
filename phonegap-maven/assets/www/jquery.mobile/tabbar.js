function loaded() {
  document.addEventListener("deviceready", onDeviceReady, false);
}
 
function onDeviceReady() {
  // Initializating TabBar
  nativeControls = window.plugins.nativeControls;
  nativeControls.createTabBar();
  
  // Books tab
  nativeControls.createTabBarItem(
    "books",
    "Books",
    null,
    {"onSelect": function() {
      books();
    }}
  );
  
  // Stats tab
  nativeControls.createTabBarItem(
    "finished",
    "Finished",
    null,
    {"onSelect": function() {
      finished();
    }}
  );
  
  // About tab
  nativeControls.createTabBarItem(
    "about",
    "About",
    null,
    {"onSelect": function() {
      about();
    }}
  );
  
  // Compile the TabBar
  nativeControls.showTabBar();
  nativeControls.showTabBarItems("books", "finished", "about");
  nativeControls.selectTabBarItem("books");
}
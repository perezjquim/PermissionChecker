# PermissionChecker

This is a implementation of a PermissionChecker. Basically, it forces the user to grant all permissions (at any time) and stops the execution until such, in order to avoid incompatibilty issues or any other kind of errors.

## Instructions

Build.gradle:
```gradle
dependencies
{
	(...)
	implementation 'com.github.perezjquim:permissionchecker:master-SNAPSHOT'
	(...)
}
```

Initialization:
(e.g. you can put this in **onCreate**)

```java
@Override
public void onCreate(Bundle savedInstance)
{
	(...)
	if (Build.VERSION.SDK_INT >= 23)
	{
		permissionChecker = new PermissionChecker(this);
		permissionChecker.start();
	}
	(...)
}
```

"Automated" restart:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
    super.onActivityResult(requestCode, resultCode, data);
	switch(requestCode)
    {
        case PermissionChecker.REQUEST_CODE:
            permissionChecker.restart();
            break;
    }
}
```

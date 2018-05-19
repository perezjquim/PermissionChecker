# PermissionChecker

This is a implementation of a PermissionChecker. Basically, it forces the user to grant all permissions (at any time) and stops the execution until such, in order to avoid incompatibilty issues or any other kind of errors.

## Install Instructions

Build.gradle (root):
```gradle
allprojects {
	repositories {
		(...)
		mavenCentral()
		maven{
		    url  'https://oss.sonatype.org/content/repositories/snapshots/'
		    name 'OSS-Sonatype'
		}
		maven { url "https://jitpack.io" }
		(...)
	}
}
```

Build.gradle (app):
```gradle
dependencies
{
	(...)
	implementation 'com.github.perezjquim:permissionchecker:master-SNAPSHOT'
	(...)
}
```

# Example of use

Initialize and start the PermissionChecker:
(e.g. you can put this in **onCreate**)

```java
@Override
public void onCreate(Bundle savedInstance)
{
	(...)
	PermissionChecker.init(this);
	(...)
}
```

To keep it running:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
	super.onActivityResult(requestCode, resultCode, data);
	switch(requestCode)
	{
		case PermissionChecker.REQUEST_CODE:
			PermissionChecker.restart();
			break;
	}
}
```
